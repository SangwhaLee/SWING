import { useEffect, useRef, useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import axios from "axios";

import {
  CaretLeft,
  CaretLeftFill,
  CaretRight,
  CaretRightFill,
} from "react-bootstrap-icons";
import { colors } from "../styles/ColorPalette";
import { CommonBtn, GameTitle, PlayerProfile } from "../styles/CommonEmotion";
import { H1 } from "../styles/Fonts";
import {
  GameRoundNav,
  HistoryContent,
  HistoryContentContainer,
  HistoryPictureContainer,
  Picture,
  SinglePicContainer,
  UserInfoBox,
  UserNickName,
} from "../styles/HistoryEmotion";
import { MyPageWrapper } from "../styles/MyPageEmotion";
import { API_URL } from "../config";
import { useRecoilState, useSetRecoilState } from "recoil";
import { userState } from "../recoil";

import { Autoplay, Navigation } from "swiper";
import { Swiper, SwiperSlide } from "swiper/react";
import "swiper/css";
import ModalClosable from "../components/ModalClosable";

function HistoryDetail() {
  const navigate = useNavigate();
  const location = useLocation();

  const [date, setDate] = useState(location.state.date);
  const [rank, setRank] = useState(location.state.rank);
  const [historyId, setHistoryId] = useState(location.state.gameId);

  const [user, setUser] = useRecoilState(userState);

  const [picDatas, setPicDatas] = useState([]);

  const [leftHover, setLeftHover] = useState(false);
  const [rightHover, setRightHover] = useState(false);
  const navigationPrevRef = useRef(null);
  const navigationNextRef = useRef(null);

  const swiperRef = useRef();
  const [round, setRound] = useState(1);

  const [previewModal, setPreviewModal] = useState(false);
  const [previewUrl, setPreviewUrl] = useState();

  const openPic = (url) => {
    swiperRef.current.autoplay.stop();
    setPreviewUrl(url);
    setPreviewModal(true);
  };

  const renderPics = (pics) => {
    if (pics) {
      return pics.map((pic, idx) => {
        return (
          <SinglePicContainer key={idx}>
            <Picture src={pic.roundImageUrl} />
            <UserInfoBox>
              <PlayerProfile
                src={pic.profileImageUrl}
                width={3}
                maxWidth={3}
                height={3}
                className="profile"
              />
              <UserNickName>{pic.nickname}</UserNickName>
              <CommonBtn
                width={"5rem"}
                minWidth={"3.5rem"}
                height={45}
                font={1.1}
                color={colors.gameYellow200}
                hoverColor={colors.gameYellow300}
                fontColor={colors.gameBlue500}
                fontWeight={700}
                className="save-btn"
                onClick={() => openPic(pic.roundImageUrl)}>
                보기
              </CommonBtn>
            </UserInfoBox>
          </SinglePicContainer>
        );
      });
    }
  };

  const getHistoryDetail = () => {
    axios
      .get(`${API_URL}/doodle/game/${user.userId}/${historyId}`, {})
      .then((res) => {
        if (res.status === 200) {
          console.log(res.data.resultList);
          setPicDatas(res.data.resultList);
        }
      })
      .catch((err) => console.error(err));
  };

  useEffect(() => {
    getHistoryDetail();
  }, []);
  return (
    <>
      {previewModal ? (
        <ModalClosable modalShow={previewModal} setModalShow={setPreviewModal}>
          <img src={previewUrl} alt="preview" />
        </ModalClosable>
      ) : null}
      <MyPageWrapper>
        <GameTitle>
          <H1
            color={colors.white}
            outline={colors.gameBlue500}
            outlineWeight={2}
            align="center"
            className="historyName">
            {date}자 게임 | 랭킹: {rank}등
          </H1>
        </GameTitle>
        <div className="flex history-btn">
          <CommonBtn
            color={colors.gamePink200}
            fontColor={colors.gameBlue500}
            fontWeight={700}
            font={1.1}
            padding="0.5rem 1rem"
            margin="0 0 0.5rem 0"
            onClick={() => navigate("/history")}>
            목록으로
          </CommonBtn>
        </div>
        <HistoryContentContainer>
          <HistoryContent>
            <GameRoundNav>
              {leftHover ? (
                <CaretLeftFill
                  className="left"
                  ref={navigationPrevRef}
                  onClick={() => swiperRef.current.slidePrev()}
                  onMouseLeave={() => setLeftHover(false)}
                />
              ) : (
                <CaretLeft
                  className="left"
                  onMouseEnter={() => setLeftHover(true)}
                />
              )}
              <div className="roundNum">ROUND {round}</div>
              {rightHover ? (
                <CaretRightFill
                  className="right"
                  ref={navigationNextRef}
                  onClick={() => swiperRef.current.slideNext()}
                  onMouseLeave={() => setRightHover(false)}
                />
              ) : (
                <CaretRight
                  className="right"
                  onMouseEnter={() => setRightHover(true)}
                />
              )}
            </GameRoundNav>
            <HistoryPictureContainer
              onMouseEnter={() => swiperRef.current.autoplay.stop()}
              onMouseLeave={() => swiperRef.current.autoplay.start()}>
              <Swiper
                onSwiper={(swiper) => {
                  swiperRef.current = swiper;
                }}
                slidesPerView={1}
                autoplay={{
                  delay: 5000, // 5초에 한번씩 자동 재생
                  disableOnInteraction: true,
                }}
                navigation={{
                  // 버튼 사용자 지정
                  nextEl: navigationNextRef.current,
                  prevEl: navigationPrevRef.current,
                }}
                onBeforeInit={(swiper) => {
                  swiper.params.navigation.prevEl = navigationPrevRef.current;
                  swiper.params.navigation.nextEl = navigationNextRef.current;
                  swiper.navigation.update();
                }}
                onSlideChange={() => {
                  setRound(swiperRef.current.activeIndex + 1);
                }}
                modules={[Autoplay, Navigation]}
                className="mySwiper">
                {/* 1라운드 */}
                <SwiperSlide className="slideContainer">
                  {renderPics(picDatas[0])}
                </SwiperSlide>
                {/* 2라운드 */}
                <SwiperSlide className="slideContainer">
                  {renderPics(picDatas[1])}
                </SwiperSlide>
                {/* 3라운드 */}
                <SwiperSlide className="slideContainer">
                  {renderPics(picDatas[2])}
                </SwiperSlide>
                {/* 4라운드 */}
                <SwiperSlide className="slideContainer">
                  {renderPics(picDatas[3])}
                </SwiperSlide>
                {/* 5라운드 */}
                <SwiperSlide className="slideContainer">
                  {renderPics(picDatas[4])}
                </SwiperSlide>
              </Swiper>
            </HistoryPictureContainer>
            {/* {renderPics} */}
          </HistoryContent>
        </HistoryContentContainer>
      </MyPageWrapper>
    </>
  );
}

export default HistoryDetail;
