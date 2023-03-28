import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
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
} from '../styles/SpeedoodleEmotion';
import { GameTitle, CommonInput, CommonBtn } from '../styles/CommonEmotion';
import { H1, H2, H3, H4, H5, P1, P2, SmText } from '../styles/Fonts';
import { colors } from '../styles/ColorPalette';
import Pagination from '../components/PaginatorBar';
import ModalClosable from '../components/ModalClosable';
import { AI_API_URL, API_URL } from '../config';
import {
  ArrowClockwise,
  AwardFill,
  PersonFill,
  LockFill,
} from 'react-bootstrap-icons';

function Speedoodle() {
  const navigate = useNavigate();
  const [activeMode, setActiveMode] = useState('MODE');
  const [createModalShow, setCreateModalShow] = useState(false);
  const [codeModalShow, setCodeModalShow] = useState(false);
  const [isHard, setIsHard] = useState(false);
  const [isLock, setIsLock] = useState(false);
  const [code, setCode] = useState('');
  const [roomId, setRoomId] = useState(null);
  const [inputCode, setInputCode] = useState('');
  const [wrongCode, setWrongCode] = useState(false);
  const options = [
    { value: 'roomNum', name: '방번호' },
    { value: 'title', name: '방제목' },
  ];

  const modeOptions = [
    { value: 'ALL', name: 'ALL' },
    { value: 0, name: 'EASY' },
    { value: 1, name: 'HARD' },
  ];

  let roomList = [];

  // 방 목록 가져오는 함수
  const getRoomList = () => {
    axios
      .get(`${API_URL}/doodle/rooms`, {
        // headers: {
        //   'Access-Token': '',
        // },
      })
      .then((res) => {
        console.log(res);
      })
      .catch((err) => console.error(err));
  };

  useEffect(() => {
    getRoomList();
  }, []);

  // const roomList = [
  //   {
  //     mode: 0,
  //     name: '밥아저씨를 꿈꾼다',
  //     roomId: 123,
  //     leaderNickname: '행복한초코',
  //     userCnt: 2,
  //     code: null,
  //   },
  //   {
  //     mode: 1,
  //     name: '뽀삐랑 놀자~',
  //     roomId: 125,
  //     leaderNickname: '귀여운뽀삐',
  //     userCnt: 4,
  //     code: '012345',
  //   },
  //   {
  //     mode: 1,
  //     name: '저는 진심입니다',
  //     roomId: 121,
  //     leaderNickname: '데이비드',
  //     userCnt: 3,
  //     code: null,
  //   },
  //   {
  //     mode: 0,
  //     name: '재밌는 스피두들',
  //     roomId: 127,
  //     leaderNickname: '쪼안나',
  //     userCnt: 3,
  //     code: '111111',
  //   },
  //   {
  //     mode: 0,
  //     name: '한판만 하고 잘게요',
  //     roomId: 128,
  //     leaderNickname: '맹지니',
  //     userCnt: 2,
  //     code: null,
  //   },
  //   {
  //     mode: 1,
  //     name: '나 이겨보셈',
  //     roomId: 111,
  //     leaderNickname: '인두목',
  //     userCnt: 3,
  //     code: '123445',
  //   },
  //   {
  //     mode: 1,
  //     name: 'welcome to speedoodle',
  //     roomId: 116,
  //     leaderNickname: 'Amily',
  //     userCnt: 2,
  //     code: null,
  //   },
  //   {
  //     mode: 0,
  //     name: '일이삼사오육칠팔구십일이삼사오육칠팔구십일이삼사오육칠팔구십',
  //     roomId: 119,
  //     leaderNickname: 'SSAFY8th',
  //     userCnt: 5,
  //     code: null,
  //   },
  // ];

  const inputOption = options.map((option, idx) => (
    <option value={option.value} key={idx}>
      {option.name}
    </option>
  ));

  const modeOption = modeOptions.map((option, idx) => (
    <option value={option.value} key={idx}>
      {option.name}
    </option>
  ));

  const rooms = roomList?.map((room) => (
    <Room
      color={room.mode === 0 ? colors.gameBlue100 : colors.gamePink200}
      key={room.roomId}
    >
      <H4 align='center'>{room.mode === 0 ? 'EASY' : 'HARD'}</H4>
      <RoomTitleContainer>
        <P2 align='center'>방번호 [{room.roomId}]</P2>
        <P1 align='center'>{room.name}</P1>
      </RoomTitleContainer>
      <FlexContainer>
        <RoomIconContainer>
          <AwardFill />
          <P2 margin='0 0 0 1rem'>{room.leaderNickname}</P2>
        </RoomIconContainer>
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
        onClick={() => enterRoom(room.roomId, room.code)}
      >
        {room.closed ? (
          <LockFill style={{ fontSize: '24px' }} />
        ) : (
          <H5 align='center'>ENTER</H5>
        )}
      </CommonBtn>
    </Room>
  ));

  // select에서 모드 선택에 따른 필터 변경
  const handleChangeMode = (e) => {
    setActiveMode(() => e.target.value);
  };

  // 방만들기 모달창 오픈
  const openModal = () => {
    setIsLock(false);
    setCreateModalShow(true);
  };

  // 방만들기 모달에서 모드 변경
  const handleCreateRoomMode = () => {
    setIsHard((prev) => !prev);
  };

  // 방만들기 모달에서 비밀방 클릭 여부
  const handleChangeIsLock = () => {
    setIsLock((prev) => !prev);
  };

  // 비밀번호 생성 및 입력시 6자리로 제한
  const handleOnInputLength = (e) => {
    if (e.target.value.length > e.target.maxLength)
      e.target.value = e.target.value.slice(0, e.target.maxLength);
  };

  // 방목록 api 새로고침 함수
  const refreshRoomList = () => {};

  // 방 입장버튼 눌렀을 때 비밀방 여부에 따라 라우터제공
  const enterRoom = (id, code) => {
    if (code !== null) {
      setWrongCode(false);
      setCodeModalShow(true);
      setCode(code);
      setRoomId(id);
    } else {
      navigate(`/speedoodle/room/${id}`);
    }
  };

  // 유저가 입력한 비밀방 코드값
  const changeCode = (e) => {
    setInputCode(e.target.value);
  };

  // 유저가 입력한 코드와 비밀방 코드 비교 함수
  const compareCode = () => {
    if (code === inputCode) {
      setWrongCode(false);
      navigate(`/speedoodle/room/${roomId}`);
    } else setWrongCode(true);
  };

  return (
    <>
      {/* speedoodle 방생성 모달 */}
      <ModalClosable
        modalShow={createModalShow}
        setModalShow={setCreateModalShow}
      >
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
              border={isHard ? 'none' : `3px solid ${colors.gameBlue500}`}
              onClick={handleCreateRoomMode}
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
              border={isHard ? `3px solid ${colors.gameBlue500}` : 'none'}
              onClick={handleCreateRoomMode}
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
          />
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
              <input
                type='checkbox'
                id='isLock'
                checked={isLock}
                onChange={handleChangeIsLock}
              ></input>
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
            onClick={compareCode}
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
          <H1
            color={colors.white}
            outline={colors.gameBlue500}
            outlineWeight={2}
            align='center'
          >
            SPEEDOODLE
          </H1>
        </GameTitle>
        <SpeedoodleContentContainer>
          <FlexContainer>
            <span>
              <SelectInput>{inputOption}</SelectInput>
              <CommonInput
                minWidth='32vw'
                height='55'
                font='1.2'
                padding='0 1rem'
                border={'none'}
                placeholder='방번호/방제목을 입력하세요.'
              />
              <CommonBtn
                height='55'
                font='1.5'
                fontColor={colors.gameBlue500}
                color={colors.white}
                padding='0.75rem 2rem'
                margin='0 0 0 1rem'
                border={'none'}
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

          <RoomContainer>{rooms}</RoomContainer>
        </SpeedoodleContentContainer>
        <Pagination
          total={42}
          limit={8}
          page={1}
          Ppage={1}
          setPage={1}
          setPpage={1}
        ></Pagination>
      </SpeedoodleWrapper>
    </>
  );
}

export default Speedoodle;
