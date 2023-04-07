import React, { useState, useEffect, useRef } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import { AI_API_URL, API_URL, getCookie } from '../config';

import {
  GameContainer,
  RoundHeader,
  CanvasContainer,
  Keyword,
  BtnContainer,
  ResultContainer,
} from '../styles/SpeedoodleGameEmotion';
import { colors } from '../styles/ColorPalette';
import { H1, H2, H3, H4, H5, H6, P1, P2, SmText } from '../styles/Fonts';
import { AlarmFill, Pause } from 'react-bootstrap-icons';
import { CommonBtn } from '../styles/CommonEmotion';
import Stopwatch from './Stopwatch';
import ReadyText from './ReadyText';
import ModalBasic from './ModalBasic';
import { useRecoilState } from 'recoil';
import { userState, speedoodleGameState, timeMessage, timeList } from '../recoil';

function SpeedoodleGame(props) {
  const navigate = useNavigate();
  const [running, setRunning] = useState(false);
  const [roundCnt, setRoundCnt] = useState(5);
  const [record, setRecord] = useState('');
  const [finish, setFinish] = useState(false);
  const [resultModalShow, setResultModalShow] = useState(false);
  const [finalResultModalShow, setFinalResultModalShow] = useState(false);
  const [readyGame, setReadyGame] = useState(false);
  const [isFinal, setIsFinal] = useState(false);

  const [keyword, setKeyword] = useState(props.keywords);
  const [timeLimits, setTimeLimits] = useState(props.limits);
  const [gameMode, setGameMode] = useState(props.isMode);

  const [keywordIdx, setKeywordIdx] = useState(0);
  const [isGameStart, setIsGameStart] = useRecoilState(speedoodleGameState);
  const [isTimeMessage, setIsTimeMessage] = useRecoilState(timeMessage);
  const [isTimeList, setIsTimeList] = useRecoilState(timeList);

  const [aiAnswer, setAiAnswer] = useState([]);
  const [isCorrect, setIsCorrect] = useState(false);
  const [isDrawFinish, setIsDrawFinish] = useState(false);
  const [showAnswers, setShowAnswers] = useState('');

  const [recordSc, setRecordSc] = useState(0);
  const [recordMs, setRecordMs] = useState(0);
  const [user, setUser] = useRecoilState(userState);

  const [rankResult, setRankResult] = useState([]);
  const [roundImage, setRoundImage] = useState(null);

  let canvasRef = useRef(null);
  let canvas;
  let pos = {
    drawable: false,
    X: -1,
    Y: -1,
  };
  let ctx;

  useEffect(() => {
    canvas = canvasRef.current;
    canvas.addEventListener('mousedown', initDraw);
    canvas.addEventListener('mousemove', draw);
    canvas.addEventListener('mouseup', finishDraw);
    canvas.addEventListener('mouseout', finishDraw);
    canvas.setAttribute('width', window.innerWidth * 0.49);
    canvas.setAttribute('height', window.innerHeight * 0.38);
    ctx = canvas.getContext('2d');
    ctx.fillStyle = 'white';
    ctx.fillRect(0, 0, canvas.width, canvas.height);
    ctx.lineWidth = 5;
    ctx.strokeStyle = 'black';
  }, []);

  const getPosition = (e) => {
    return { X: e.offsetX, Y: e.offsetY };
  };

  // 마우스를 눌렀을 때 실행하는 함수
  const initDraw = (e) => {
    ctx.beginPath();
    pos = { drawable: true, ...getPosition(e) };
    ctx.lineTo(pos.X, pos.Y);
    ctx.stroke();
  };

  // 마우스 누른 상태로 움직일 때 실행하는 함수(이걸로 그려짐)
  const draw = (e) => {
    if (pos.drawable) {
      pos = { ...pos, ...getPosition(e) };
      ctx.lineTo(pos.X, pos.Y);
      ctx.strokeStyle = 'black';
      ctx.stroke();
    }
  };

  // 마우스를 떼거나, 마우스가 범위를 벗어났을 때 그림 그리는 걸 멈추는 함수
  const finishDraw = () => {
    pos = { drawable: false, X: -1, Y: -1 };
    setIsDrawFinish(() => true);
  };

  // 캔버스를 전체 지우는 함수
  const resetCanvas = () => {
    canvas = canvasRef.current;
    canvas.getContext('2d').clearRect(0, 0, canvas.width, canvas.height);
    canvas.getContext('2d').fillStyle = 'white';
    canvas.getContext('2d').fillRect(0, 0, canvas.width, canvas.height);
  };

  // 5 라운드가 끝난 시점에서 각자의 결과를 websocket으로 전송
  useEffect(() => {
    if(roundCnt !== 5) {
      const formdata = new FormData();
      // formdata.append('userId', user.userId);
      console.log(user.userId);
      console.log(keyword[keywordIdx].roundId);
      const data = {
        userId: user.userId,
        roundId: keyword[keywordIdx].roundId,
      }
      const saveRoundResultDto = new Blob([JSON.stringify(data)], {type: 'application/json'});

      formdata.append('saveRoundResultDto', saveRoundResultDto);
      formdata.append('image', roundImage);

      axios({
        url:`${API_URL}/doodle/round`,
        method: 'POST',
        data: formdata,
        headers: {
          'Access-Token': getCookie('accessToken'),
          'Content-Type': 'multipart/form-data',
        },
      })
      .then((res) => {
        console.log(res);
        if(roundCnt > 0) {
          setKeywordIdx((prev) => prev + 1);
        }
        if(roundCnt === 0){
          const myTime = `${recordSc}:${recordMs}`;
          // console.log(myTime);
          setIsTimeMessage(myTime);
        }
      })
      .catch((err) => {
        console.log(err);
      })
    }
  }, [roundCnt]);

  //websocket으로 END 메시지가 도착할 경우 각자의
  useEffect(() => {
    if(isTimeList?.userList.length === isTimeList.userNum){
      console.log(`이게 받은 값:`);
      let temp = isTimeList.userList;
      temp.sort((a, b) => {
        // Convert each time string to seconds
        const aSeconds = parseInt(a.split(":")[0], 10) * 100 + parseInt(a.split(":")[1], 10);
        const bSeconds = parseInt(b.split(":")[0], 10) * 100 + parseInt(b.split(":")[1], 10);
        
        return aSeconds - bSeconds; // Sort in ascending order
      });
      setRankResult(temp);
    }
  }, [isTimeList]);

  useEffect(() => {
    if (rankResult.length !== 0) {
      const myTime = `${recordSc}:${recordMs}`;
      let myIdx = (rankResult.findIndex(obj => obj.context === myTime) + 1);
      const gameId = keyword[0].gameId;
      axios({
        method: 'POST',
        url: `${API_URL}/doodle/game/${user.userId}/${gameId}/${myIdx}`,
        headers: {
          'Access-Token': getCookie('accessToken'),
        },
      })
        .then((res) => {
          console.log(res);
        })
        .catch((err) => {
          console.log(err);
        });
    }
  }, [rankResult]);

  useEffect(() => {
    const getAnswer = () => {
      canvas = canvasRef.current;
      canvas.toBlob((blob) => {
        setRoundImage(blob);
        const formdata = new FormData();
        formdata.append('answer', blob);
        formdata.append('answered', aiAnswer);

        axios
          .post(`${AI_API_URL}/doodle/check`, formdata, {
            headers: {
              'Content-Type': 'multipart/form-data',
            },
          })
          .then((res) => {
            console.log(res.data);
            console.log('ai답변들', aiAnswer);
            console.log('키워드다', keyword[keywordIdx].content);
            if (res.data.class === keyword[keywordIdx].content) {
              console.log('성공');
              setIsCorrect(() => true);
            } else {
              setAiAnswer((aiAnswer) => [...aiAnswer, res.data.class]);
            }
          })
          .catch((err) => {
            // console.log(err);
          });
      });
    };

    if (isDrawFinish === true && record === '') {
      getAnswer();
      setIsDrawFinish(() => false);
    }
  }, [isDrawFinish, record]);

  // 제한시간 다 끝났을 때 결과 모달 여는 useEffect

  useEffect(() => {
    if (finish) {
      if (record === '') {
        setRecord(() => '실패 ' + timeLimits + ': 00');
        setRecordSc((prev) => prev + timeLimits);
      }
      resetCanvas();
      setTimeout(() => {
        handleResultModal();
      }, 1000);
    }
  }, [finish]);

  useEffect(() => {
    if (isFinal) {
      setTimeout(() => {
        handleFinalResultModal();
      }, 1000);
    }
  }, [isFinal]);

  // 결과 모달 여는 함수

  const handleResultModal = () => {
    setAiAnswer(() => []);
    if (recordMs >= 100) {
      setRecordSc((prev) => prev + 1);
      setRecordMs((prev) => prev - 100);
    }
    setResultModalShow(true);
    setTimeout(() => {
      if (roundCnt === 1) {
        //임시
        setRoundCnt((prev) => prev - 1);
        setTimeout(() => {
          setIsFinal(true);
        }, 2000);
      }
      setResultModalShow(false);
      setRecord(() => '');

      if (roundCnt > 1) {
        setFinish(false);
        setRoundCnt((prev) => prev - 1);
        setTimeout(() => {
          setReadyGame(false);
        }, 1000);
      }
    }, 3000);
  };

  // 최종 결과 모달 여는 함수

  const handleFinalResultModal = () => {
    setFinalResultModalShow(true);
    setTimeout(() => {
      setFinalResultModalShow(false);
      // 다시 대기방 상태로 회귀
      setIsGameStart(false);
      setIsTimeList([]);
    }, 3000);
  };
  // readyText 가 끝났을 때 타이머 handle하는 함수
  useEffect(() => {
    if (readyGame) {
      handleRunning();
    }
  }, [readyGame]);

  // 타이머 작동 여부 함수
  const handleRunning = () => {
    setRunning((prev) => !prev);
  };

  return (
    <>
      {/* 최종결과 제공 모달 */}
      <ModalBasic modalShow={finalResultModalShow} setModalShow={setFinalResultModalShow}>
        <H3 color={colors.gameBlue500}>최종 결과</H3>
        <ResultContainer>
          {rankResult?.map((item, idx) => (
            <H3 color={colors.gameBlue300} margin='5rem 0 0 0' key={idx}>
              순위: {idx + 1}등({item.context})
            </H3>
          ))}
        </ResultContainer>
        <div style={{ width: '24vw', height: '24vw' }}></div>
      </ModalBasic>
      {/* 각 라운드 결과 제공 모달 */}
      <ModalBasic modalShow={resultModalShow} setModalShow={setResultModalShow}>
        <H2>Round {6 - roundCnt}</H2>
        <div className='flex-column align-items'>
          <H4 padding='2rem 0' color={colors.black}>
            {record}
          </H4>
          <H3 padding='4rem 0' color={colors.gameBlue300}>
            정답은 {keyword && keyword[keywordIdx].meaningKr}
          </H3>
        </div>
        {/* <div style={{ width: '24vw', height: '24vw' }}></div> */}
        <P2 margin='2rem 0 0'>다른 유저들의 그림은 히스토리에서 확인하실 수 있습니다.</P2>
      </ModalBasic>
      <GameContainer>
        <ReadyText readyGame={readyGame} setReadyGame={setReadyGame}></ReadyText>
        <RoundHeader>
          {roundCnt > 0 ? <H4>Round {6 - roundCnt}</H4> : <H4></H4>}

          <span style={{ display: 'flex' }}>
            <AlarmFill style={{ fontSize: '30px', marginRight: '0.5rem' }} />
            {running ? (
              <Stopwatch
                running={running}
                seconds={timeLimits}
                setRunning={setRunning}
                setRecord={setRecord}
                setFinish={setFinish}
                correct={isCorrect}
                setIsCorrect={setIsCorrect}
                setRecordSc={setRecordSc}
                setRecordMs={setRecordMs}
              ></Stopwatch>
            ) : (
              <div
                style={{
                  width: '3vw',
                  display: 'flex',
                  justifyContent: 'start',
                }}
              >
                <H5 color={colors.gameBlue500}>{timeLimits}:00</H5>
              </div>
            )}
          </span>
        </RoundHeader>

        <CanvasContainer>
          <Keyword>
            <H4 align='center'>
              {keyword && (timeLimits === 30 ? keyword[keywordIdx].meaningEn : keyword[keywordIdx].content)}
            </H4>
          </Keyword>
          <div
            style={{
              width: '100%',
              height: '100%',
              display: running ? 'none' : 'block',
              position: 'absolute',
            }}
          ></div>
          <canvas ref={canvasRef}></canvas>
        </CanvasContainer>
        <BtnContainer>
          <div
            style={{
              width: '6vw',
              opacity: '0',
            }}
          ></div>
          <div
            style={{
              width: '20vw',
              padding: '0.5rem',
              color: `${colors.gameBlue500}`,
              backgroundColor: 'white',
              border: `1px solid ${colors.gameBlue500}`,
              borderRadius: '0.75rem',
              textAlign: 'center',
            }}
          >
            {record !== '' && record !== '실패 ' + timeLimits + ': 00' ? (
              <p style={{ fontWeight: 'bold', margin: '0', color: '#0000FF' }}>성공</p>
            ) : (
              <p style={{ fontWeight: 'bold', margin: '0' }}>{aiAnswer[aiAnswer.length - 1]}</p>
            )}
          </div>
          <CommonBtn
            onClick={resetCanvas}
            color={colors.gameBlue300}
            fontColor={colors.white}
            padding='0.5rem 1.5rem'
            font='0.75'
            margin='0 2rem 0 0'
          >
            <P1>지우기</P1>
          </CommonBtn>
        </BtnContainer>
      </GameContainer>
    </>
  );
}
export default SpeedoodleGame;
