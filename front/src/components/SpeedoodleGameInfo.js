import React, { useState, useRef, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

import {
  GameInfoContainer,
  RoomTitle,
  RoomInfoContainer,
  RoomInfo,
  Chat,
  ChattingContainer,
  ChattingInputContainer,
  ChatInput,
  GameExplain,
  GameModeContainer,
  GameMode,
  BtnContainer,
} from '../styles/SpeedoodleGameInfoEmotion';
import SpeedoodleGame from '../components/SpeedoodleGame';
import { CommonBtn, CommonInput } from '../styles/CommonEmotion';
import { colors } from '../styles/ColorPalette';
import { H1, H2, H4, H5, H6, P1, P2, SmText } from '../styles/Fonts';
import { API_URL, getCookie } from '../config';
import { useRecoilState } from 'recoil';
import { userState, speedoodleGameState } from '../recoil';

import { SendFill } from 'react-bootstrap-icons';

function SpeedoodleGameInfo(props) {
  const navigate = useNavigate();
  const chatWindowRef = useRef(null);
  const [isMode, setIsMode] = useState(props.gameInfo.mode);
  const [roomInfo, setRoomInfo] = useState(props.gameInfo);
  const [bgColor, setBgColor] = useState(null);
  const [gameData, setGameData] = useState(null);

  const [limits, setLimits] = useState(0);
  const [isGameStart, setIsGameStart] = useRecoilState(speedoodleGameState);
  const [isLocked, setIsLocked] = useState(false);

  const [user, setUser] = useRecoilState(userState);

  // const user = JSON.parse(window.localStorage.getItem('user'));
  const messages = useRef(null);

  useEffect(() => {
    scrollToBottom();
  }, [props.chatData]);

  useEffect(() => {
    setIsMode(props.propMode);
    if (props.propMode === 1) {
      setLimits(() => 30);
    } else {
      setLimits(() => 20);
    }
  }, [props.propMode]);

  useEffect(() => {
    if (props.gameKey !== null && gameData === null) {
      console.log('key전달 완료');
      setIsLocked(true);
      setGameData(props.gameKey);
    }
  }, [props.gameKey]);

  useEffect(() => {
    if (gameData !== null) {
      console.log('드디어 게임 시작');
      console.log(gameData);
      setBgColor(`${colors.gameBlue100}`);
      setIsGameStart(true);
    }
  }, [gameData]);

  useEffect(() => {
    setIsMode(props.gameInfo.mode);
    setRoomInfo(props.gameInfo);
    if (props.gameInfo.mode) {
      setLimits(() => 30);
    } else {
      setLimits(() => 20);
    }
  }, []);

  useEffect(() => {
    if (isGameStart === false && isLocked === true) {
      if (roomInfo.leaderId === user.useId) {
        axios
          .put(`${API_URL}/doodle/start/${roomInfo.roomId}`)
          .then((res) => {
            console.log('겜끝! 풀게요!');
          })
          .catch((err) => console.error(err));
      }
      setBgColor(`${colors.white}`);
      setIsLocked(false);
    }
  }, [isGameStart]);

  useEffect(() => {
    if (!isLocked) {
      setGameData(null);
    }
  }, [isLocked]);

  // 모드 변경
  const onClickEasyMode = () => {
    setIsMode(0);
    props.ModeMessage(0);
    // handleChangeMode();
  };
  const onClickHardMode = () => {
    setIsMode(1);
    props.ModeMessage(1);
    // handleChangeMode();
  };

  const linkCopy = () => {
    navigator.clipboard.writeText(window.location.href);
    alert('링크가 복사되었습니다. 초대할 친구에게 링크를 공유하세요!');
  };

  // 시작버튼 눌렀을 때
  const handleGameStart = () => {
    //방 잠금 설정
    axios
      .put(`${API_URL}/doodle/start/${roomInfo.roomId}`)
      .then((res) => {
        if (res.status === 200) {
          console.log('방시작! 잠굴게요!');
          handleGameInfo();
          // setTimeout(() => {
          //   setBgColor(`${colors.gameBlue100}`);
          //   setIsGameStart(true);
          // }, 1000);
        }
      })
      .catch((err) => console.error(err));
  };
  // 게임 시작(게임정보가져오기)
  const handleGameInfo = async () => {
    await axios
      .get(`${API_URL}/doodle/start/${roomInfo.roomId}/${roomInfo.name}`)
      .then((res) => {
        console.log('게임시작 정보', res);
      })
      .catch((err) => console.error(err));
  };

  const scrollToBottom = () => {
    messages.current?.scrollIntoView({ behavior: 'smooth' });
  };

  //방 나가기
  const exitRoom = () => {
    setIsGameStart(false);
    // axios
    //   .delete(`${API_URL}/doodle/room/leave/${roomInfo.roomId}/${user.userId}`)
    //   .then((res) => {
    //     if (res.status === 200) {

    //     }
    //   })
    //   .catch((err) => console.error(err));
    props.stompDisconnect();
    console.log('방퇴장합니다!');
    navigate('/speedoodle');
  };
  // 보낼 메세지 저장해놓기
  const saveMessage = (e) => {
    props.setChatInput(e.target.value);
  };
  // 저장해둔 메세지 보내기
  const sendMessage = () => {
    if (props.chatInput !== '') {
      props.SendMessage();
      props.setChatInput('');
    }
  };

  //모드 변경 api 전달
  const handleChangeMode = () => {
    console.log(`지금 모드는 ${isMode}`);
    axios
      .put(`${API_URL}/doodle/room/${roomInfo.roomId}/${isMode}`, null, {})
      .then((res) => {
        console.log('모드', res);
      })
      .catch((err) => console.error(err));
  };

  useEffect(() => {
    handleChangeMode();
  }, [isMode]);

  return (
    <>
      <GameInfoContainer color={bgColor}>
        {isGameStart ? (
          <SpeedoodleGame
            style={{ width: '100%', height: '100%' }}
            isMode={props.propMode}
            limits={limits}
            keywords={gameData}
            roomInfo={props.gameInfo}
            timeValue={props.timeValue}
          ></SpeedoodleGame>
        ) : (
          <div style={{ width: '100%', height: '100%' }}>
            <RoomTitle>
              <H5 align='center' color={colors.gameBlue500}>
                {props.gameInfo.name}
              </H5>
            </RoomTitle>
            <RoomInfoContainer>
              <RoomInfo>
                <GameExplain>
                  <H5 align='center' color={colors.gameBlue500}>
                    게임 설명
                  </H5>
                  <P2
                    align='center'
                    color={colors.gameBlue500}
                    style={{ wordBreak: 'keep-all' }}
                  >
                    라운드 별로 키워드가 주어지고, 시간내에 AI가 키워드를 맞추면
                    성공하는 게임입니다. 라운드는 총 5라운드로 구성되어 있으며,
                    가장 적은 시간으로 5문제를 성공시킨 유저가 1등을 차지하게
                    됩니다. <br />
                    (AI가 키워드를 맞추지 못했을 경우 각 모드의 제한시간이
                    기록이 됩니다.)
                  </P2>
                </GameExplain>

                {props.gameInfo.leaderNickname === user.nickname ? (
                  <GameModeContainer>
                    <GameMode
                      fontColor={colors.gameBlue500}
                      color={colors.gameBlue100}
                      border={
                        isMode === 1
                          ? 'none'
                          : `3px solid ${colors.gameBlue500}`
                      }
                      onClick={onClickEasyMode}
                    >
                      <H6 align='center'>EASY MODE</H6>
                      <P2 align='center' style={{ wordBreak: 'keep-all' }}>
                        EASY MODE는 키워드가 영단어로 제시됩니다.
                        <br /> 제한시간 20초
                      </P2>
                    </GameMode>
                    <GameMode
                      fontColor={colors.gameBlue500}
                      color={colors.gamePink200}
                      border={
                        isMode === 1
                          ? `3px solid ${colors.gameBlue500}`
                          : 'none'
                      }
                      onClick={onClickHardMode}
                    >
                      <H6 align='center'>HARD MODE</H6>
                      <P2 align='center'>
                        HARD MODE는 키워드가 영영사전의 뜻으로 제시됩니다.{' '}
                        <br />
                        제한시간 30초
                      </P2>
                    </GameMode>
                  </GameModeContainer>
                ) : (
                  <GameModeContainer>
                    <GameMode
                      fontColor={colors.gameBlue500}
                      color={colors.gameBlue100}
                      border={
                        isMode === 1
                          ? 'none'
                          : `3px solid ${colors.gameBlue500}`
                      }
                    >
                      <H6 align='center'>EASY MODE</H6>
                      <P2 align='center' style={{ wordBreak: 'keep-all' }}>
                        EASY MODE는 키워드가 영단어로 제시됩니다.
                        <br /> 제한시간 20초
                      </P2>
                    </GameMode>
                    <GameMode
                      fontColor={colors.gameBlue500}
                      color={colors.gamePink200}
                      border={
                        isMode === 1
                          ? `3px solid ${colors.gameBlue500}`
                          : 'none'
                      }
                    >
                      <H6 align='center'>HARD MODE</H6>
                      <P2 align='center'>
                        HARD MODE는 키워드가 영영사전의 뜻으로 제시됩니다.{' '}
                        <br />
                        제한시간 30초
                      </P2>
                    </GameMode>
                  </GameModeContainer>
                )}

                <BtnContainer>
                  <div style={{ width: '60%' }}>
                    <CommonBtn
                      color={colors.gameYellow100}
                      fontColor={colors.gameBlue500}
                      width='100%'
                      font='0.75'
                      padding='0.5rem 0'
                      margin='0 0 1rem 0'
                      onClick={linkCopy}
                    >
                      <P1 align='center'>초대하기</P1>
                    </CommonBtn>
                    <CommonBtn
                      color={colors.gray200}
                      fontColor={colors.gray400}
                      width='100%'
                      font='0.75'
                      padding='0.5rem 0'
                      onClick={exitRoom}
                    >
                      <P1 align='center'>나가기</P1>
                    </CommonBtn>
                  </div>

                  {props.gameInfo.leaderNickname === user.nickname ? (
                    <CommonBtn
                      color={colors.gameBlue500}
                      fontColor={colors.white}
                      width='calc(40% - 1rem)'
                      font='0.75'
                      onClick={handleGameStart}
                    >
                      <H4 align='center'>시작하기</H4>
                    </CommonBtn>
                  ) : (
                    <CommonBtn
                      color={colors.gray500}
                      fontColor={colors.white}
                      width='calc(40% - 1rem)'
                      font='0.75'
                    >
                      <H4 align='center'>시작하기</H4>
                    </CommonBtn>
                  )}
                </BtnContainer>
              </RoomInfo>
              <Chat>
                <ChattingContainer useRef={chatWindowRef}>
                  {props.chatData.map((item, idx) => {
                    return (
                      <P2 ref={messages} key={idx}>
                        {item[0]}: {item[1]}
                      </P2>
                    );
                  })}
                </ChattingContainer>
                <ChattingInputContainer>
                  <ChatInput
                    onChange={saveMessage}
                    value={props.chatInput}
                    onKeyDown={(e) => e.key === 'Enter' && sendMessage()}
                  />
                  <button
                    style={{
                      all: 'unset',
                      cursor: 'pointer',
                      color: `${colors.gameBlue500}`,
                    }}
                    onClick={sendMessage}
                  >
                    <SendFill />
                  </button>
                </ChattingInputContainer>
              </Chat>
            </RoomInfoContainer>
          </div>
        )}
      </GameInfoContainer>
    </>
  );
}
export default SpeedoodleGameInfo;
