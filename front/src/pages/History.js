import React, { useState } from 'react';
import { MyPageWrapper, HistoryHeader } from '../styles/MyPageEmotion';
import {
  HistoryContent,
  HistoryContentContainer,
  SingleHistoryList,
} from '../styles/HistoryEmotion';
import { GameTitle } from '../styles/CommonEmotion';
import { H1 } from '../styles/Fonts';
import { colors } from '../styles/ColorPalette';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import { API_URL, getCookie } from '../config';
import { useRecoilState } from 'recoil';
import { userState } from '../recoil';

function History() {
  const navigate = useNavigate();
  const [user, setUser] = useRecoilState(userState);
  const [historyList, setHistoryList] = useState([]);

  const changeTimeStampToDate = (timestamp) => {
    let yy = timestamp.slice(0, 4);
    let mm = timestamp.slice(5, 7);
    let dd = timestamp.slice(8, 10);
    const date = `${yy}년 ${mm}월 ${dd}일`;

    return date;
  };

  const renderHistoryList = historyList.map((history) => {
    return (
      // api 연결 후엔 key값 history id로 설정하기
      <SingleHistoryList
        key={history.gameId}
        onClick={() =>
          navigate(`/history/${history.gameId}`, {
            state: {
              date: changeTimeStampToDate(history.playTime),
              rank: history.rank,
              gameId: history.gameId,
            },
          })
        }
      >
        <div className='history-date'>
          {changeTimeStampToDate(history.playTime)}
        </div>
        <div className='history-title'>{history.roomName}</div>
        <div className='history-rank'>{history.rank}등</div>
      </SingleHistoryList>
    );
  });

  const getHistoryList = async () => {
    await axios
      .get(`${API_URL}/doodle/history/${user.userId}`, {
        headers: {
          'Access-Token': getCookie('accessToken'),
        },
      })
      .then((res) => {
        console.log(res);
        setHistoryList(res.data.gameHistoryList);
      });
  };

  useState(() => {
    getHistoryList();
  }, []);

  return (
    <>
      <MyPageWrapper>
        <GameTitle>
          <H1
            color={colors.white}
            outline={colors.gameBlue500}
            outlineWeight={2}
            align='center'
          >
            게임 히스토리
          </H1>
        </GameTitle>
        <div className='desc'>최근 10건</div>
        <HistoryContentContainer>
          <HistoryHeader border>
            <div className='date'>날짜</div>
            <div className='roomname'>방제목</div>
            <div className='rank'>등수</div>
          </HistoryHeader>
          <HistoryContent>
            {historyList.length > 0 ? (
              renderHistoryList
            ) : (
              <div className='no-history'>히스토리 목록이 없습니다.</div>
            )}
          </HistoryContent>
        </HistoryContentContainer>
      </MyPageWrapper>
    </>
  );
}

export default History;
