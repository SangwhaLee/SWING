import React, { useState, useEffect, useRef } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

import { SpeedoodleWrapper } from '../styles/SpeedoodleEmotion';
import SpeedoodleUser from '../components/SpeedoodleUser';
import SpeedoodleGameInfo from '../components/SpeedoodleGameInfo';
import { SpeedoodleRoomContainer } from '../styles/SpeedoodleRoomEmotion';
import { GameTitle } from '../styles/CommonEmotion';
import { H1, H2, H4, H5, P1, P2, SmText } from '../styles/Fonts';
import { colors } from '../styles/ColorPalette';
import SockJS from 'sockjs-client';
import { Stomp } from '@stomp/stompjs';
import { API_URL, getCookie } from '../config';
import { useRecoilState } from 'recoil';
import { userState, speedoodleGameState, timeMessage, timeList } from '../recoil';

function SpeedoodleRoom() {
  const navigate = useNavigate();
  const [gameRoomInfo, setGameRoomInfo] = useState({});
  const [chatInput, setChatInput] = useState('');
  const [isGameStart, setIsGameStart] = useRecoilState(speedoodleGameState);
  const [isTimeMessage, setIsTimeMessage] = useRecoilState(timeMessage);
  const [isTimeList, setIsTimeList] = useRecoilState(timeList);
  const [chatData, setChatData] = useState([]);
  const [user, setUser] = useRecoilState(userState);
  const [userList, setUserList] = useState([]);
  const [changeUser, setChangeUser] = useState(null);
  const [propMode, setPropMode] = useState(null);
  const [isStart, setIsStart] = useState(null);
  const [gameKey, setGameKey] = useState(null);
  const [timeValue, setTimeValue] = useState([]);
  const [endCheck, setEndCheck] = useState(null);
  const stompRef = useRef(null);
  const roomUrl = new URL(window.location.href).pathname.split('/');
  const lengthUrl = roomUrl.length;
  const room_id = roomUrl[lengthUrl - 1];

  // room 랜더를 위해 불러오는 api

  useEffect(() => {
    getRoomInfo();
  }, []);

  const getRoomInfo = async () => {
    await axios
      .get(`${API_URL}/doodle/room/info/${room_id}/${user.userId}`)
      .then((res) => {
        if (res.status === 200) {
          console.log(res.data);
          setUserList(res.data.chatUserList);
          setGameRoomInfo(() => res.data);
        }
      })
      .catch((err) => {
        console.error(err);
      });
  };

  //SpeedoodleRoom이 시작될때
  useEffect(() => {
    if (!stompRef.current && gameRoomInfo !== null) {
      stompConnect();
    }

    return () => {
      if (stompRef.current?.connected) {
        setIsGameStart(false);
        stompDisconnect();
      }
    }
  }, []);

  useEffect(() => {
    if (changeUser !== null) {
      if (changeUser.messageType === 'ENTER') {
        if (changeUser.nickname !== user.nickname) {
          const tempUser = {
            userId: changeUser.userId,
            nickname: changeUser.nickname,
            profileImageUrl: changeUser.profileImageUrl,
            roomId: room_id,
          };
          setUserList([...userList, tempUser]);
        }
      } else if (changeUser.messageType === 'LEAVE') {
        console.log('나가는거 봤다');
        if (changeUser.nickname === gameRoomInfo.roomInfo.leaderNickname) {
          navigate('/speedoodle');
        } else {
          const tempList = userList.filter(
            (user) => user.userId !== changeUser.userId
          );
          setUserList(tempList);
        }
      }
    }
  }, [changeUser]);

  //웹소켓 오픈하고 서버에 연결
  const stompConnect = () => {
    try {
      const stomp = Stomp.over(function () {
        return new SockJS(`${API_URL}/speedoodle/room`);
      });
      stomp.connect({}, (message) => {
        console.log('STOMP connection established');
        const data = {
          messageType: 'ENTER',
          userId: `${user.userId}`,
          nickname: `${user.nickname}`,
          profileImageUrl: `${user.profileImageUrl}`,
          roomId: room_id,
        };
        stomp.send('/pub/send', {}, JSON.stringify(data));
        stomp.subscribe(
          `/sub/${room_id}`,
          (Ms) => {
            const msObj = JSON.parse(Ms.body);
            if (msObj.messageType === 'ENTER') {
              console.log('왔다');
              setChangeUser(msObj);
            } else if (msObj.messageType === 'LEAVE') {
              setChangeUser(msObj);
            } else if (msObj.messageType === 'COMMON') {
              setChatData((chatData) => [
                ...chatData,
                [msObj.publisher, msObj.message],
              ]);
            }
            else if(msObj.messageType === 'MODE') {
              console.log(msObj);
              setPropMode(msObj.data);
            } else if (msObj.messageType === 'START') {
              setIsStart(true);
              console.log(msObj);
              setGameKey(msObj.roundInfoList);
            }
            else if(msObj.messageType === 'END') {
              console.log(msObj);
              const time = msObj.data.split(':');
              console.log(time);
              const context = {
                user: msObj.publisher,
                context: msObj.data,
              }
              setIsTimeList({
                userNum: msObj.userNum,
                userList: [...isTimeList.userList, context],
              });
              // setTimeValue([...timeValue,(Number(time[0])*100 + Number(time[1]))]);
              // console.log(Number(time[0])*100 + Number(time[1]));
            }
            console.log(msObj);
          },
          {}
        );
      });
      stompRef.current = stomp;
    } catch (error) {
      console.log(error);
    }
  };

  useEffect(() => {
    if(isTimeMessage !== null){
      SendTimeMessage(isTimeMessage, userList.length);
      console.log(isTimeMessage);
    }
  },[isTimeMessage])


  //웹소켓 연결 끊기
  const stompDisconnect = () => {
    try {
      console.log('나간다');
      const data = {
        messageType: 'LEAVE',
        userId: `${user.userId}`,
        nickname: `${user.nickname}`,
        profileImageUrl: `${user.profileImageUrl}`,
        roomId: room_id,
      };
      console.log(data);
      stompRef.current.send('/pub/send', {}, JSON.stringify(data));
      stompRef.current.disconnect(
        () => {
          console.log('STOMP connection closed');
        },
        {
          subscriptionId: `/sub/${room_id}`,
        }
      );
    } catch (error) {}
  };

  //결과 누적 시간 보낼때
  const SendTimeMessage = (val,num) => {
    console.log("나 끝났어!");
    const data = {
      messageType: 'END',
      data: val,
      publisher: user.nickname,
      roomId: room_id,
      userNum: num,
    }
    if (stompRef.current?.connected) {
      stompRef.current.send('/pub/send', {}, JSON.stringify(data));
    } else {
      console.log('STOMP connection is not open');
    }
  };

  //일반 채팅 보낼때
  const SendMessage = () => {
    // stomp.debug = null;
    const data = {
      messageType: 'COMMON',
      roomId: room_id,
      publisher: user.nickname,
      message: chatInput,
    };
    if (stompRef.current?.connected) {
      console.log(stompRef.current.connected);
      stompRef.current.send('/pub/send', {}, JSON.stringify(data));
    } else {
      console.log(stompRef.current.connected);
      console.log('STOMP connection is not open');
    }
  };

  //모드 변경 할 때
  const ModeMessage = (value) => {
    const data = {
      messageType: 'MODE',
      data: value,
      roomId: room_id,
    };
    if (stompRef.current?.connected) {
      console.log(stompRef.current.connected);
      stompRef.current.send('/pub/send', {}, JSON.stringify(data));
    } else {  
      console.log('STOMP connection is not open');
    }
  };

  const preventClose = (e) => {
    e.preventDefault();
    e.returnValue = ''; //Chrome에서 동작하도록; deprecated
  };

  useEffect(() => {
    (() => {
      window.addEventListener('beforeunload', preventClose);
    })();

    return () => {
      window.removeEventListener('beforeunload', preventClose);
    };
  }, []);

  const preventGoBack = () => {
    window.history.pushState(null, '', window.location.href);
  };

  useEffect(() => {
    window.history.pushState(null, '', window.location.href);
    window.addEventListener('popstate', preventGoBack);

    return () => {
      window.removeEventListener('popstate', preventGoBack);
    };
  }, []);

  return (
    <>
      <SpeedoodleWrapper>
        <GameTitle>
          <H1
            color={colors.white}
            outline={colors.gameBlue500}
            outlineWeight={2}
            align='center'
          >
            SPEEDOODLE
          </H1>
        </GameTitle>
        <SpeedoodleRoomContainer>
          {gameRoomInfo?.roomInfo ? (
            <>
              <SpeedoodleUser
                leader={gameRoomInfo?.roomInfo.leaderNickname}
                data={userList}
              ></SpeedoodleUser>

              {isGameStart ? (
                <SpeedoodleGameInfo
                  gameInfo={gameRoomInfo.roomInfo}
                  // start={isGameStart}
                  // setIsGameStart={setIsGameStart}
                  roomId={room_id}
                ></SpeedoodleGameInfo>
              ) : (
                <SpeedoodleGameInfo
                  gameInfo={gameRoomInfo.roomInfo}
                  // start={isGameStart}
                  // setIsGameStart={setIsGameStart}
                  chatInput={chatInput}
                  setChatInput={setChatInput}
                  chatData={chatData}
                  stompDisconnect={stompDisconnect}
                  SendMessage={SendMessage}
                  ModeMessage={ModeMessage}
                  propMode={propMode}
                  isStart={isStart}
                  gameKey={gameKey}
                  timeValue={timeValue}
                  // isMode={isMode}
                ></SpeedoodleGameInfo>
              )}
            </>
          ) : (
            <>loading중</>
          )}
        </SpeedoodleRoomContainer>
      </SpeedoodleWrapper>
    </>
  );
}

export default SpeedoodleRoom;
