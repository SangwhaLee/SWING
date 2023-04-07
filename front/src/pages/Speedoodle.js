import React, { useState, useEffect, useRef } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import { userState } from '../recoil';
import { useRecoilValue } from 'recoil';

import {
  SpeedoodleWrapper,
  SpeedoodleContentContainer,
  SelectInput,
  RoomContainer,
  Room,
  RoomTitleContainer,
  RoomIconContainer,
  CreateRoomContainer,
  FlexContainer,
  HmmGif,
  RoomNicknameContainer,
} from '../styles/SpeedoodleEmotion';
import { GameTitle, CommonInput, CommonBtn } from '../styles/CommonEmotion';
import { H1, H2, H3, H4, H5, P1, P2, SmText } from '../styles/Fonts';
import { colors } from '../styles/ColorPalette';
import Pagination from '../components/PaginatorBar';
import ModalClosable from '../components/ModalClosable';
import { API_URL, getCookie } from '.././config';
import { ArrowClockwise, AwardFill, PersonFill, LockFill } from 'react-bootstrap-icons';

function Speedoodle() {
  const navigate = useNavigate();
  // roomList 관련 useState
  const [roomList, setRoomList] = useState([]);
  const [renderRoomList, setRenderRoomList] = useState([]);
  const [activeMode, setActiveMode] = useState(2);
  // 검색 관련 useState
  const [type, setType] = useState('roomId');
  const [searchInput, setSearchInput] = useState('');
  // 방생성 모달 관련 useState
  const [createModalShow, setCreateModalShow] = useState(false);
  const [isOkTitle, setIsOkTitle] = useState('');
  const [makeMode, setMakeMode] = useState(0);
  const [isLock, setIsLock] = useState(false);
  const [makeCode, setMakeCode] = useState('');
  const [makeTitle, setMakeTitle] = useState('');
  // 비밀번호 입력 모달 useState
  const [codeModalShow, setCodeModalShow] = useState(false);
  const [compareCode, setCompareCode] = useState('');
  const [roomId, setRoomId] = useState(null);
  const [inputCode, setInputCode] = useState('');
  const [wrongCode, setWrongCode] = useState(false);
  // 페이지네이션 관련 useState
  const [limit, setLimit] = useState(8);
  const [page, setPage] = useState(1);
  const [Ppage, setPpage] = useState(1);
  const [offset, setOffset] = useState(0);
  // 리코일에서 가져온 유저 정보
  const user = useRecoilValue(userState);
  // const user = JSON.parse(window.localStorage.getItem('user'));

  useEffect(() => {
    const newOffset = (page - 1 + 5 * (Ppage - 1)) * limit;
    setOffset(newOffset);
  }, [page, Ppage]);

  // 검색 option
  const options = [
    { value: 'roomId', name: '방번호' },
    { value: 'name', name: '방제목' },
  ];

  const inputOption = options.map((option, idx) => (
    <option value={option.value} key={idx}>
      {option.name}
    </option>
  ));

  // 모드 option
  const modeOptions = [
    { value: 2, name: 'ALL' },
    { value: 0, name: 'EASY' },
    { value: 1, name: 'HARD' },
  ];

  const modeOption = modeOptions.map((option, idx) => (
    <option value={option.value} key={idx}>
      {option.name}
    </option>
  ));

  // 방 목록 가져오는 함수
  const getRoomList = () => {
    axios
      .get(`${API_URL}/doodle/rooms`, {})
      .then((res) => {
        setRoomList([...res.data.roomList]);
        setRenderRoomList([...res.data.roomList]);
      })
      .catch((err) => console.error(err));
  };

  useEffect(() => {
    getRoomList();
  }, []);

  // 모드 셀렉터에 따른 랜더링되는 방 변경
  useEffect(() => {
    handleRenderRoomList(activeMode);
  }, [activeMode]);

  // 방 컴포넌트
  const rooms = renderRoomList?.slice(offset, offset + limit).map((room) => (
    <Room color={room.mode === 0 ? colors.gameBlue100 : colors.gamePink200} key={room.roomId}>
      <H4 align='center'>{room.mode === 0 ? 'EASY' : 'HARD'}</H4>
      <RoomTitleContainer>
        <div className='roomid'>방번호 [{room.roomId}]</div>
        <div className='roomname'>{room.name}</div>
      </RoomTitleContainer>
      <FlexContainer>
        <RoomNicknameContainer>
          <AwardFill className='award' />
          <div className='nickname'>
            {room.leaderNickname.length > 20 ? room.leaderNickname.substr(0, 18) + '...' : room.leaderNickname}
          </div>
        </RoomNicknameContainer>
        <RoomIconContainer>
          <PersonFill />
          <P2 margin='0 0 0 1rem'>{room.userCnt} / 6</P2>
        </RoomIconContainer>
      </FlexContainer>
      <CommonBtn
        width='100%'
        padding='0.5rem 0'
        color={colors.white}
        fontColor={colors.gameBlue500}
        border='none'
        font='1'
        onClick={() =>
          room.started === 0 ? handleEnterRoom(room.roomId, room.code) : alert('게임이 시작된 방입니다.')
        }
      >
        {room.code !== '' ? (
          room.started === 0 ? (
            <LockFill style={{ fontSize: '24px' }} />
          ) : (
            <LockFill style={{ color: `${colors.gray300}`, fontSize: '24px' }} />
          )
        ) : room.started === 0 ? (
          <H5 align='center'>ENTER</H5>
        ) : (
          <H5 align='center' color={colors.gray300}>
            STARTED
          </H5>
        )}
      </CommonBtn>
    </Room>
  ));

  // 모드 선택에 따라 랜더될 room data 변경
  const handleRenderRoomList = (mode) => {
    if (mode === '0') {
      setRenderRoomList(roomList.filter((room) => room.mode === 0));
    } else if (mode === '1') {
      setRenderRoomList(roomList.filter((room) => room.mode === 1));
    } else {
      setRenderRoomList([...roomList]);
    }
  };

  // 검색 select에 따른 type 변경
  const handleType = (e) => {
    setType(() => e.target.value);
  };

  // 검색창 입력 받아오기
  const handleSearchInput = (e) => {
    setSearchInput(() => e.target.value);
  };

  // 검색창 입력 전달
  const handleSearch = () => {
    handleSearchRoomList([type, searchInput]);
  };
  // 검색창 입력에 따른 결과 반환
  const handleSearchRoomList = (condition) => {
    const type = condition[0];
    const search = condition[1];
    if (type === 'roomId') {
      setRenderRoomList(
        roomList.filter((room) => {
          const title = room.roomId;
          return title.toString().includes(search);
        }),
      );
    } else {
      setRenderRoomList(roomList.filter((room) => room.name.includes(search)));
    }
  };

  // select에서 모드 선택에 따른 필터 변경
  const handleChangeMode = (e) => {
    setActiveMode(() => e.target.value);
    handleRenderRoomList(activeMode);
  };

  // 방만들기 모달창 오픈
  const openModal = () => {
    // 방만들기 모달 열때 값 리셋
    setMakeTitle('');
    setMakeMode(0);
    setMakeCode('');
    setIsLock(false);

    // 방만들기 모달 오픈
    setCreateModalShow(true);
  };

  // 방만들기 방제목 값
  const handleMakeTitle = (e) => {
    const regExp = /^[a-zA-Z0-9가-힣ㄱ-ㅎㅏ-ㅣ]{1,30}$/g;
    if (regExp.test(e.target.value)) {
      setIsOkTitle(() => true);
      setMakeTitle(e.target.value);
    } else {
      setIsOkTitle(() => false);
    }
  };

  // 방만들기 모달에서 모드 변경
  const handleCreateRoomMode = (mode) => {
    if (makeMode !== mode) {
      makeMode ? setMakeMode(0) : setMakeMode(1);
    }
  };

  // 방만들기 모달에서 비밀방 클릭 여부
  const handleChangeIsLock = () => {
    setIsLock((prev) => !prev);
  };

  // 방만들기 비밀번호 값
  const handleMakeCode = (e) => {
    setMakeCode(e.target.value);
  };

  // 방만들기 api
  const makeRoom = () => {
    axios
      .post(`${API_URL}/doodle/room`, {
        code: makeCode,
        leaderId: user.userId,
        mode: makeMode,
        name: makeTitle,
      })
      .then((res) => {
        if (res.status === 200) {
          console.log(res);
          navigate(`/speedoodle/room/${res.data.roomId}`);
        }
      })
      .catch((err) => console.error(err));
  };

  // 비밀번호 생성 및 입력시 6자리로 제한
  const handleOnInputLength = (e) => {
    if (e.target.value.length > e.target.maxLength) e.target.value = e.target.value.slice(0, e.target.maxLength);
  };

  // 방목록 api 새로고침 함수
  const refreshRoomList = () => {
    getRoomList();
  };

  //방 입장 api
  const enterRoom = (id) => {
    // axios
    //   .post(`${API_URL}/doodle/room/enter/${id}/${user.userId}`, null, {
    //     headers: {
    //       'Access-Token': getCookie('accessToken'),
    //       'Content-Type': `application/json`,
    //     },
    //   })
    //   .then((res) => {
    //     if (res.status === 200) {
    //       navigate(`/speedoodle/room/${id}`);
    //     }
    //   })
    //   .catch((err) => console.error(err));
    navigate(`/speedoodle/room/${id}`);
  };

  // 방 입장버튼 눌렀을 때 비밀방 여부에 따라 라우터제공
  const handleEnterRoom = (id, code) => {
    if (code !== '') {
      setWrongCode(false);
      setCodeModalShow(true);
      setCompareCode(code);
      setRoomId(id);
    } else {
      axios
        .get(`${API_URL}/doodle/room/info/${id}/${user.userId}`)
        .then((res) => {
          if (res.status === 200) {
            if (res.data.roomInfo.started === 0) {
              enterRoom(id);
            } else {
              alert('게임이 시작된 방입니다.');
            }
          }
        })
        .catch((err) => {
          console.error(err);
        });
    }
  };

  // 유저가 입력한 비밀방 코드값
  const changeCode = (e) => {
    setInputCode(e.target.value);
  };

  // 유저가 입력한 코드와 비밀방 코드 비교 함수
  const handleCompareCode = () => {
    if (compareCode === inputCode) {
      setWrongCode(false);
      axios
        .get(`${API_URL}/doodle/room/info/${roomId}/${user.userId}`)
        .then((res) => {
          if (res.status === 200) {
            if (res.data.roomInfo.started === 0) {
              enterRoom(roomId);
            } else {
              alert('게임이 시작된 방입니다.');
            }
          }
        })
        .catch((err) => {
          console.error(err);
        });
    } else setWrongCode(true);
  };

  return (
    <>
      {/* speedoodle 방생성 모달 */}
      <ModalClosable modalShow={createModalShow} setModalShow={setCreateModalShow}>
        <H2 color={colors.gameBlue500}>방만들기</H2>
        <CreateRoomContainer>
          <FlexContainer>
            <CommonBtn
              padding='0 1.7rem'
              margin='0 2rem 0 0'
              width='20vw'
              height='164'
              font='2.5'
              fontColor={colors.gameBlue500}
              color={colors.gameBlue100}
              border={makeMode ? 'none' : `3px solid ${colors.gameBlue500}`}
              onClick={() => handleCreateRoomMode(0)}
            >
              <H4 align='center'>EASYMODE</H4>
              <P1 align='center' style={{ wordBreak: 'keep-all' }}>
                EASY MODE는 키워드가 영단어로 제시됩니다.
                <br /> 제한시간 20초
              </P1>
            </CommonBtn>
            <CommonBtn
              style={{ wordBreak: 'keep-all' }}
              padding='0 1.7rem'
              width='20vw'
              height='164'
              font='2.5'
              fontColor={colors.gameBlue500}
              color={colors.gamePink200}
              border={makeMode ? `3px solid ${colors.gameBlue500}` : 'none'}
              onClick={() => handleCreateRoomMode(1)}
            >
              <H4 align='center'>HARD MODE</H4>
              <P1 align='center'>
                HARD MODE는 키워드가 영영사전의 뜻으로 제시됩니다. <br />
                제한시간 30초
              </P1>
            </CommonBtn>
          </FlexContainer>
          <CommonInput
            style={{
              backgroundColor: `${colors.gray100}`,
              margin: '1rem 0',
            }}
            minWidth='100%'
            height='55'
            padding='0 1rem'
            font='1.2'
            border={'none'}
            placeholder='방제목을 입력하세요.'
            onChange={handleMakeTitle}
          />
          {isOkTitle ? (
            <p
              style={{
                margin: '0 0 0.75rem 0',
                textAlign: 'left',
                color: 'green',
              }}
            >
              올바른 제목입니다.
            </p>
          ) : (
            <p
              style={{
                margin: '0 0 0.75rem 0',
                textAlign: 'left',
                color: 'red',
              }}
            >
              영문, 한글, 숫자로 이루어진 제목으로 지어주세요. (공백 미포함)
            </p>
          )}

          <FlexContainer>
            <div
              style={{
                display: 'flex',
                alignItems: 'center',
                minWidth: '120px',
                height: '55px',
                padding: '0 1rem',
                marginRight: '2rem',
                borderRadius: '0.5em',
                backgroundColor: `${colors.gameYellow100}`,
                boxSizing: 'border-box',
              }}
            >
              <label htmlFor='isLock'>
                <LockFill /> 비밀방
              </label>
              <input type='checkbox' id='isLock' checked={isLock} onChange={handleChangeIsLock}></input>
            </div>
            <CommonInput
              disabled={isLock ? false : true}
              type='number'
              maxLength='6'
              style={{
                backgroundColor: `${colors.gray100}`,
              }}
              width='100%'
              height='55'
              padding='0 1rem'
              font='1.2'
              placeholder='비밀번호 숫자 6자리'
              onInput={handleOnInputLength}
              onChange={handleMakeCode}
            />
          </FlexContainer>
          <div style={{ marginTop: '2rem' }}>
            <CommonBtn
              padding='0.75rem 1.5rem'
              font='1.5'
              fontColor={colors.white}
              color={colors.gray400}
              margin='0 2rem 0 0'
              onClick={() => {
                setCreateModalShow(false);
                setIsLock(false);
              }}
            >
              <H4>취소</H4>
            </CommonBtn>
            <CommonBtn
              padding='0.75rem 1.5rem'
              font='1.5'
              fontColor={colors.white}
              color={colors.gameBlue500}
              onClick={() => (isOkTitle ? makeRoom() : alert('방제목을 확인해주세요.'))}
            >
              <H4>확인</H4>
            </CommonBtn>
          </div>
        </CreateRoomContainer>
      </ModalClosable>
      {/* speedoodle 비밀방 입장시 비밀번호 입력 모달 */}
      <ModalClosable modalShow={codeModalShow} setModalShow={setCodeModalShow}>
        <H3 align='center'>비밀번호</H3>
        <FlexContainer style={{ marginTop: '2rem' }}>
          <CommonInput
            maxLength='6'
            type='number'
            style={{
              backgroundColor: `${colors.gray100}`,
            }}
            width='100%'
            height='55'
            padding='0 1rem'
            font='1.2'
            placeholder='비밀번호 숫자 6자리'
            onChange={changeCode}
            onInput={handleOnInputLength}
          />
          <CommonBtn
            margin='0 0 0 1rem'
            padding='0.5em 1rem'
            width='5vw'
            color={colors.gameBlue100}
            fontColor={colors.gameBlue500}
            font='1.2'
            onClick={handleCompareCode}
          >
            <P1 align='center'>확인</P1>
          </CommonBtn>
        </FlexContainer>
        <div
          style={{
            width: '100%',
            marginTop: '0.5rem',
            visibility: `${wrongCode ? 'visible' : 'hidden'}`,
          }}
        >
          <P2 color={colors.gamePink500}>비밀번호가 일치하지 않습니다.</P2>
        </div>
      </ModalClosable>
      <SpeedoodleWrapper>
        <GameTitle>
          <H1 color={colors.white} outline={colors.gameBlue500} outlineWeight={2} align='center'>
            SPEEDOODLE
          </H1>
        </GameTitle>
        <SpeedoodleContentContainer>
          <FlexContainer>
            <span>
              <SelectInput onChange={handleType} value={type}>
                {inputOption}
              </SelectInput>
              <CommonInput
                minWidth='32vw'
                height='55'
                font='1.2'
                padding='0 1rem'
                border={'none'}
                placeholder='방번호/방제목을 입력하세요.'
                onChange={handleSearchInput}
                value={searchInput}
              />
              <CommonBtn
                height='55'
                font='1.5'
                fontColor={colors.gameBlue500}
                color={colors.white}
                padding='0.75rem 2rem'
                margin='0 0 0 1rem'
                border={'none'}
                onClick={handleSearch}
              >
                Search
              </CommonBtn>
            </span>
            <SelectInput onChange={handleChangeMode} value={activeMode}>
              {modeOption}
            </SelectInput>

            <CommonBtn
              width='55px'
              font='1.5'
              fontColor={colors.gameBlue500}
              color={colors.white}
              border={'none'}
              onClick={refreshRoomList}
            >
              <ArrowClockwise style={{ fontSize: '30px' }} />
            </CommonBtn>
            <CommonBtn
              height='55'
              font='1.5'
              fontColor={colors.gameBlue500}
              color={colors.gamePink200}
              padding='0.75rem 2rem'
              margin='0 0 0 1rem'
              border={'none'}
              onClick={openModal}
            >
              <H4>방만들기</H4>
            </CommonBtn>
          </FlexContainer>
          {renderRoomList.length > 0 ? (
            <RoomContainer>{rooms}</RoomContainer>
          ) : (
            <div
              style={{
                display: 'flex',
                justifyContent: 'center',
                alignItems: 'center',
                height: '100%',
              }}
            >
              <H3 align='center' color={colors.white}>
                방 목록이 없습니다.
              </H3>
              <HmmGif />
            </div>
          )}
        </SpeedoodleContentContainer>
        <Pagination
          total={renderRoomList.length}
          limit={limit}
          page={page}
          Ppage={Ppage}
          setPage={setPage}
          setPpage={setPpage}
        ></Pagination>
      </SpeedoodleWrapper>
    </>
  );
}

export default Speedoodle;
