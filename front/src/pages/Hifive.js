import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import {
  HifiveWrapper,
  HifiveContainer,
  ProblemContainer,
  AnswerContainer,
  GameinfoContainer,
  ProblemtoalContainer,
  AnswertotalContainer,
  InputContainer,
  HifiveModalContainer,
  HifiveStatistics,
  GameImage,
} from '../styles/HifiveEmotion';
import { CommonInput, CommonBtn, GameTitle } from '../styles/CommonEmotion';
import { H1, H2, H3, H5 } from '../styles/Fonts';
import { colors } from '../styles/ColorPalette';
import ModalBasic from '../components/ModalBasic';
import { HeartFill } from 'react-bootstrap-icons';
import LeaderBoard from '../components/LeaderBoard';
import { AI_API_URL, API_URL, getCookie } from '../config';
import { useRecoilState } from 'recoil';
import { userState } from '../recoil';
import axios from 'axios';

function Hifive() {
  const navigate = useNavigate();

  const [imageSet, setImageSet] = useState([]);
  const [inputValue, setInputValue] = useState('');
  const [isVibrating, setIsVibrating] = useState(false);
  const [borderColor, setBorderColor] = useState('white');
  const [imageCheck, setImageCheck] = useState([
    [100, false],
    [100, false],
    [100, false],
    [100, false],
    [100, false],
  ]);
  const [lifeStack, setLifeStack] = useState(5);
  const [answerStack, setAnswerStack] = useState([]);
  const [wrongWords, setWrongWords] = useState([]);
  const [resultValue, setresultValue] = useState();
  const [chanceCnt, setChanceCnt] = useState(null);
  const [finalValue, setFinalValue] = useState(null);

  const [modalLoading, setModalLoading] = useState(false);

  const [otherRank, setOtherRank] = useState();
  const [mine, setMine] = useState();

  const [user, setUser] = useRecoilState(userState);
  const [scoreStack, setScoreStack] = useState(0);
  const [tryCnt, setTryCnt] = useState(0);
  const [corCnt, setCorCnt] = useState(0);

  // 페이지가 렌더링 될 때 유저의 횟수를 호출
  useEffect(() => {
    axios({
      url: `${API_URL}/user/five/${user.userId}`,
      method: 'GET',
      headers: {
        'Access-Token': getCookie('accessToken'),
      },
    })
      .then((res) => {
        setChanceCnt(res.data.fiveCnt);
      })
      .catch((err) => {});
  }, []);

  // 유저의 횟수가 남아있는가 여부에 따라 값이 달라진다.
  useEffect(() => {
    if (chanceCnt === 0) {
      getResult();
    } else {
      axios({
        url: `${API_URL}/five`,
        method: 'GET',
        headers: {
          'Access-Token': getCookie('accessToken'),
        },
      })
        .then((res) => {
          setImageSet(res.data.wordList);
        })
        .catch((err) => {});
    }
  }, [chanceCnt]);

  useEffect(() => {
    if (lifeStack === 0) {
      setWrongWords(imageSet);
      sendResult();
      imageSet.map((obj) => {
        axios({
          method: 'POST',
          url: `${API_URL}/note/word/${user.userId}/${obj.wordId}`,
          headers: {
            'Access-Token': getCookie('accessToken'),
          },
        })
          .then((res) => {})
          .catch((err) => {});
      });
    }
  }, [lifeStack]);

  useEffect(() => {
    if (corCnt === 5) {
      sendResult();
    }
  }, [corCnt]);

  // 유저가 답을 작성하면 발생하는 함수
  const handleSubmit = (e) => {
    e.preventDefault();
    let URL = `${AI_API_URL}/five/check?`;
    imageSet.forEach((element) => {
      const content = element.content.replace('_', ' ');
      URL += `solution=${content}&`;
    });
    URL += `answer=${inputValue}`;
    setTryCnt(tryCnt + 1);

    axios({
      url: URL,
      method: 'GET',
    }).then((res) => {
      handleAnswer(res.data.similarity);
    });
    setInputValue('');
  };

  // 유사도에 따라 달라진다. (0.4미만 틀이 흔들리고 빨개짐) (0.85미만 해당 사진이 흔들리고 틀이 빨개짐)
  // 0.85이상 정답 처리가 되며 사진이 사라지고 answer란에 적혀진다.
  const handleAnswer = (answers) => {
    let maxSimilar = 0;
    let maxKey = 0;
    for (let key in answers) {
      if (answers[key] > maxSimilar) {
        maxSimilar = answers[key];
        maxKey = key;
      }
    }

    let temp = [...imageCheck];
    let tempSet = [...imageSet];
    maxKey = maxKey.replace(' ', '_');

    if (maxSimilar < 0.4) {
      setIsVibrating(true);
      setBorderColor(colors.gamePink500);

      setLifeStack(lifeStack - 1);

      temp.forEach((element) => {
        element[0] = Math.ceil(element[0] * 0.9);
      });
      setImageCheck(temp);

      const timeoutId = setTimeout(() => {
        setIsVibrating(false);
        setBorderColor('white');
      }, 500);

      return () => clearTimeout(timeoutId);
    } else if (maxSimilar < 0.85) {
      const idx = imageSet.findIndex((obj) => obj.content === maxKey);
      temp[idx][1] = true;
      temp[idx][0] = Math.ceil(temp[idx][0] * maxSimilar);
      setImageCheck(temp);
      setBorderColor(colors.gamePink500);

      setLifeStack(lifeStack - 1);

      const timeoutId = setTimeout(() => {
        temp[idx][1] = false;
        setImageCheck(temp);
        setBorderColor('white');
      }, 500);

      return () => clearTimeout(timeoutId);
    } else {
      const idx = imageSet.findIndex((obj) => obj.content === maxKey);
      const answer = tempSet.splice(idx, 1);
      const answerCheck = temp.splice(idx, 1);
      answerStack.push([answer[0].content, answer[0].meaningKr]);

      setScoreStack(scoreStack + answerCheck[0][0]);
      setBorderColor(colors.gameBlue300);
      setImageSet(tempSet);
      setImageCheck(temp);
      setCorCnt(corCnt + 1);

      const timeoutId = setTimeout(() => {
        setBorderColor('white');
      }, 500);

      return () => clearTimeout(timeoutId);
    }
  };

  // 유저가 결과를 전송할때 사용하는 함수
  const sendResult = () => {
    const FiveResultDto = {
      userId: user.userId,
      dayScore: scoreStack,
      dayTry: tryCnt,
      dayCorrect: corCnt,
    };
    axios({
      url: `${API_URL}/five`,
      method: 'PUT',
      data: FiveResultDto,
      headers: {
        'Access-Token': getCookie('accessToken'),
      },
    }).then(() => {
      getResult();
    });
  };

  //서버로부터 오늘 결과 수신
  const getResult = () => {
    axios({
      url: `${API_URL}/five/${user.userId}`,
      method: 'GET',
      headers: {
        'Access-Token': getCookie('accessToken'),
      },
    })
      .then((res) => {
        const others = res.data.fiveRankList;
        const myRank = others.pop();
        setOtherRank(others);
        setMine(myRank);
        setFinalValue(res.data.fiveStat);
      })
      .catch((err) => {});
  };

  //finalValue에 변화가 생긴다는 뜻은 getResult가 호출되었다는 뜻
  //모달을 불러오는 타이밍이 되었다는 뜻
  useEffect(() => {
    if (finalValue !== null) {
      axios({
        method: 'PUT',
        url: `${API_URL}/user/five/${user.userId}/0`,
        headers: {
          'Access-Token': getCookie('accessToken'),
        },
      })
        .then((res) => {})
        .catch((err) => {});
      if (finalValue.streak === 0) {
        setresultValue(false);
        setModalLoading(true);
      } else {
        setresultValue(true);
        setModalLoading(true);
      }
    }
  }, [finalValue]);

  return (
    <>
      {/* 성공했을때 모달 */}
      <ModalBasic modalShow={resultValue === true}>
        <H1 color={colors.gamePink500} padding={'0rem 0rem 2rem 0rem'}>
          SUCCESS
        </H1>
        <HifiveModalContainer>
          {modalLoading && <LeaderBoard others={otherRank} mine={mine} />}
          {modalLoading && (
            <HifiveStatistics width={25} color={colors.gameBlue200}>
              <H2 color={colors.gameBlue500}>통계</H2>
              <div className='resultBox'>
                <div className='resultValue'>
                  <H3 color={colors.gameBlue500}>점수</H3>
                  <H3 color={colors.gameBlue500}>{scoreStack ? scoreStack : mine.score}점</H3>
                </div>
                <div className='resultValue'>
                  <H3 color={colors.gameBlue500}>누적 점수</H3>
                  <H3 color={colors.gameBlue500}>{finalValue.totalScore}점</H3>
                </div>
                <div className='resultValue'>
                  <H3 color={colors.gameBlue500}>누적 정답률</H3>
                  <H3 color={colors.gameBlue500}>
                    {Math.round((finalValue.totalCorrect / finalValue.totalTry) * 100)}%
                  </H3>
                </div>
                <div className='resultValue'>
                  <H3 color={colors.gameBlue500}>연속 성공 횟수</H3>
                  <H3 color={colors.gameBlue500}>{finalValue.streak}회</H3>
                </div>
              </div>
            </HifiveStatistics>
          )}
        </HifiveModalContainer>
        <CommonBtn
          width='34.7%'
          onClick={() => navigate('/')}
          height={74}
          font={2}
          color={colors.gameYellow300}
          hoverColor={colors.gameYellow400}
          margin={'3rem 0rem 0rem 0rem'}
          border={'none'}
        >
          메인페이지
        </CommonBtn>
      </ModalBasic>
      <ModalBasic modalShow={resultValue === false}>
        <H1 color={colors.gamePink500} padding={'0rem 0rem 2rem 0rem'}>
          FAILED
        </H1>
        <HifiveModalContainer>
          {modalLoading && <LeaderBoard others={otherRank} mine={mine} />}
          {modalLoading && (
            <HifiveStatistics width={25} color={colors.gameBlue200}>
              <H2 color={colors.gameBlue500}>통계</H2>
              <div className='resultBox'>
                <div className='resultValue'>
                  <H3 color={colors.gameBlue500}>점수</H3>
                  <H3 color={colors.gameBlue500}>{scoreStack ? scoreStack : mine.score}점</H3>
                </div>
                <div className='resultValue'>
                  <H3 color={colors.gameBlue500}>누적 점수</H3>
                  <H3 color={colors.gameBlue500}>{finalValue.totalScore}점</H3>
                </div>
                <div className='resultValue'>
                  <H3 color={colors.gameBlue500}>누적 정답률</H3>
                  <H3 color={colors.gameBlue500}>
                    {Math.round((finalValue.totalCorrect / finalValue.totalTry) * 100)}%
                  </H3>
                </div>
                <div className='resultValue'>
                  <H3 color={colors.gameBlue500}>연속 성공 횟수</H3>
                  <H3 color={colors.gameBlue500}>{finalValue.streak}회</H3>
                </div>
              </div>
            </HifiveStatistics>
          )}
          {/* 실패했을 때의 모달 */}
          {wrongWords.length > 0 ? (
            <HifiveStatistics width={12} color={colors.gameBlue200}>
              <H2 color={colors.gameBlue500}>오답</H2>
              <div className='resultBox'>
                {wrongWords.map((item, index) => {
                  return (
                    <div className='resultValue' key={index}>
                      <H5 color={colors.gameBlue500}>{item.content}</H5>
                      <H5 color={colors.gameBlue500}>{item.meaningKr}</H5>
                    </div>
                  );
                })}
              </div>
            </HifiveStatistics>
          ) : null}
        </HifiveModalContainer>
        <CommonBtn
          width='34.7%'
          onClick={() => navigate('/')}
          height={74}
          font={2}
          color={colors.gameYellow300}
          hoverColor={colors.gameYellow400}
          margin={'3rem 0rem 0rem 0rem'}
          border={'none'}
        >
          메인페이지
        </CommonBtn>
      </ModalBasic>
      <HifiveWrapper>
        <GameTitle>
          <H1 color={colors.white} outline={colors.gameBlue500} outlineWeight={2}>
            HI-FIVE
          </H1>
        </GameTitle>
        <HifiveContainer>
          <ProblemtoalContainer>
            <GameinfoContainer>
              <H3 color={colors.white}>SCORE: {scoreStack}</H3>
              <div className='heart-container'>
                <HeartFill className='heart' />
                <H3 color={colors.gamePink500}> X {lifeStack}</H3>
              </div>
            </GameinfoContainer>
            <ProblemContainer color={borderColor} vibration={isVibrating}>
              {imageSet.map((item, index) => {
                return (
                  <GameImage
                    key={index}
                    src={item.wordImageUrl}
                    alt={item}
                    opacity={0.6 - 0.05 * index}
                    vibration={imageCheck[index][1]}
                  />
                );
              })}
              {/* <object
              type='image/svg+xml'
              data='data:image/svg+xml,%3Csvg xmlns="http%3A%2F%2Fwww.w3.org%2F2000%2Fsvg" width="320" height="320" viewBox="0 0 24 24"%3E%3Cg fill="none" stroke="%2305bc02" stroke-linecap="round" stroke-linejoin="round" stroke-width="2"%3E%3Cpath stroke-dasharray="60" stroke-dashoffset="60" d="M3 12C3 7.02944 7.02944 3 12 3C16.9706 3 21 7.02944 21 12C21 16.9706 16.9706 21 12 21C7.02944 21 3 16.9706 3 12Z"%3E%3Canimate fill="freeze" attributeName="stroke-dashoffset" dur="0.5s" values="60%3B0"%2F%3E%3C%2Fpath%3E%3Cpath stroke-dasharray="14" stroke-dashoffset="14" d="M8 12L11 15L16 10"%3E%3Canimate fill="freeze" attributeName="stroke-dashoffset" begin="0.6s" dur="0.2s" values="14%3B0"%2F%3E%3C%2Fpath%3E%3C%2Fg%3E%3C%2Fsvg%3E'
              className='correctEmoji'
              ></object> */}
            </ProblemContainer>
          </ProblemtoalContainer>
          <AnswertotalContainer>
            <H3 color={colors.white} font={2}>
              ANSWERS
            </H3>
            <AnswerContainer>
              {answerStack.map((answer, index) => {
                return (
                  <div className='singleAnswer' key={index}>
                    <H5 color={colors.black}>{answer[0]}</H5>
                    <H5 color={colors.black}>{answer[1]}</H5>
                  </div>
                );
              })}
            </AnswerContainer>
          </AnswertotalContainer>
          <InputContainer onSubmit={handleSubmit}>
            <CommonInput
              width='80%'
              height={55}
              font={2}
              border={'none'}
              padding={'0rem 1rem 0rem 1rem'}
              value={inputValue}
              onChange={(e) => {
                setInputValue(e.target.value);
              }}
              autoFocus={true}
            />
            <CommonBtn
              type='submit'
              width='25%'
              height={55}
              font={2}
              color={colors.gameBlue100}
              hoverColor={colors.studyBlue200}
            >
              <H3 color={colors.black} align={'center'}>
                SUBMIT
              </H3>
            </CommonBtn>
          </InputContainer>
        </HifiveContainer>
      </HifiveWrapper>
    </>
  );
}

export default Hifive;
