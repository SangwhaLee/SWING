// import styled from '@emotion/styled/macro';
import styled, { keyframes } from 'styled-components';
import { colors } from '../styles/ColorPalette';
import HeroImg from '../assets/main.gif';
export const HomeWrapper = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;
  height: 400vh;
  background-color: ${colors.white};
  box-sizing: border-box;
  overflow-y: auto;
`;

export const HomeHeroContainer = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  align-items: center;
  width: 100%;
  height: 100vh;
  padding: 11rem 10% 3rem;
  background-color: #bbd4f2;
  box-sizing: border-box;
`;

export const HomeSentencyContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  position: relative;
  width: 100%;
  height: 100vh;
  padding: 0 10%;
  padding-top: 12rem;
  background: linear-gradient(to top right, rgba(236, 61, 89, 0.5), rgba(22, 86, 233, 0.5));
  box-sizing: border-box;

  .sentencyImg1,
  .sentencyImg2 {
    position: absolute;
    user-drag: none;
  }

  .sentencyImg1 {
    left: 5%;
    height: 75vh;
  }

  .sentencyImg2 {
    right: 5%;
    bottom: 5%;
    height: 40vh;
    transform: scaleX(-1);
  }
`;

export const HomeHiFiveContainer = styled.div`
  display: flex;
  flex-direction: column;
  position: relative;
  align-items: center;
  width: 100%;
  height: 100vh;
  padding: 0 10%;
  padding-top: 12rem;
  background-color: ${colors.gameBlue200};
  box-sizing: border-box;

  @keyframes float {
    0% {
      transform: translatey(0px);
    }
    50% {
      transform: translatey(-30px);
    }
    100% {
      transform: translatey(0px);
    }
  }

  .cloud1,
  .cloud2 {
    position: absolute;
    user-drag: none;
  }

  .cloud1 {
    left: 5%;
    animation: float 5s ease-in-out infinite;
  }

  .cloud2 {
    width: 30vw;
    right: 5%;
    bottom: -10%;
    animation: float 6s ease-in-out infinite;
  }
`;

export const HomeSpeedoodleContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  position: relative;
  width: 100%;
  height: 100vh;
  padding: 0 10%;
  padding-top: 12rem;
  background-color: ${colors.gameBlue300};
  box-sizing: border-box;

  .paint1 {
    width: 25%;
    position: absolute;
    right: 0;
    bottom: 0;
    user-drag: none;
  }

  .paint2 {
    width: 35%;
    position: absolute;
    left: 0;
    bottom: 0;
    user-drag: none;
  }
`;

export const Divider = styled.div`
  width: 100%;
  height: 5px;
`;

export const HeroIntroBox = styled.div`
  position: relative;
  display: flex;
  justify-content: space-between;
  width: 100%;
  height: 60vh;
  padding: 0 3% 0 0;
`;

export const HeroGif = styled.div`
  position: absolute;
  right: 1rem;
  width: 60%;
  height: 100%;
  background: center / contain no-repeat url(${HeroImg});
`;

export const HeroScrollMsg = styled.div``;

export const HeroScrollIconContainer = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-top: 1rem;
  box-sizing: border-box;
`;
export const upDown = keyframes`
0% {
  margin-top: 0rem;
100% {
  margin-top: 1rem;
}`;

export const HeroScrollIconAni = styled.div`
  margin-top: 1rem;
  animation: ${upDown} 0.5s linear infinite alternate;
`;

export const UserInfoBox = styled.div`
  display: flex;
  flex-direction: column;
  position: fixed;
  top: 10rem;
  right: 3%;
  width: 16rem;
  padding: 2rem;
  border-radius: 1rem;
  background-color: ${colors.studyBlue500};
  box-shadow: 4px 4px 4px rgba(0, 0, 0, 0.25);
  z-index: 99;

  .userInfo {
    align-items: center;
  }

  .nickname {
    color: ${colors.white};
    font-weight: 700;
    font-size: 1.2rem;
    padding: 0 0 0 1rem;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
  }
`;

export const UserCouponBox = styled.div`
  display: flex;
  justify-content: space-between;
  padding: 1rem 0;

  .couponInfo {
    font-size: 1.1rem;
    color: ${colors.white};
  }

  .couponCnt {
    font-weight: 700;
    padding-left: 0.5rem;
    color: ${colors.studyPink200};
  }
`;

export const UserBtnBox = styled.div`
  display: flex;
  justify-content: space-between;
`;

export const HomeRankBtn = styled.div`
  width: 5rem;
  height: 5rem;
  border-radius: 1rem;
  background-color: ${colors.black};
  font-size: 2.5rem;
  color: ${colors.gameYellow200};
  cursor: pointer;

  position: absolute;
  left: 1%;
  display: flex;
  justify-content: center;
  align-items: center;
  animation: bounce 1s infinite;

  .text {
    position: absolute;
    top: -2rem;
    font-size: 1.5rem;
    color: ${colors.white};
  }

  @keyframes bounce {
    0%,
    20%,
    50%,
    80%,
    100% {
      -webkit-transform: translateY(0);
    }
    40% {
      -webkit-transform: translateY(-0.5rem);
    }
    60% {
      -webkit-transform: translateY(-0.25rem);
    }
  }

  &:hover {
    box-shadow: 4px 4px 4px rgba(0, 0, 0, 0.25);
  }
`;

export const GameInfoContainer = styled.div`
  width: 50%;
  min-width: 30rem;
  display: flex;
  flex-direction: column;
  align-items: center;

  .slideContainer {
    margin-left: 0;
    margin-bottom: 1rem;
    width: 100%;
    height: 55vh;
    border: 3px solid ${colors.gamePink500};
    border-radius: 1rem;
    background-color: rgba(0, 0, 0, 0.5);
    box-sizing: border-box;
  }

  .slide {
    box-sizing: border-box;
    display: flex;
    flex-direction: column;

    padding: 2rem 3rem;
    align-items: center;
    font-size: 1.4rem;
    color: ${colors.gray100};

    @media screen and (max-width: 1760px) {
      .order,
      .desc {
        font-size: 1.3rem !important;
      }

      .title {
        font-size: 1.8rem !important;
      }

      .warningBox > .flex {
        padding-bottom: 1rem;
      }
    }

    @media screen and (max-width: 1530px) {
      .order,
      .desc {
        font-size: 1.2rem !important;
      }
    }

    @media screen and (max-width: 1280px) {
      .order,
      .desc {
        font-size: 1.1rem !important;
      }
    }
  }

  .slide .title {
    font-size: 2rem;
    font-weight: 700;
  }

  .swiper-pagination-bullet {
    width: 0.7rem;
    height: 0.7rem;
    background-color: ${colors.gamePink400};
  }

  .how-to-img {
    width: 100%;
    max-height: 60%;
    object-fit: contain;
    margin: 1.5rem 0;
  }

  .order {
    padding-right: 1rem;
  }

  .desc {
    flex-grow: 1;
  }

  .speedoodleDesc {
    padding-top: 2rem;
  }

  .warningBox {
    width: 100%;
    display: flex;
    flex-direction: column;
    text-align: left;
    margin: 1.5rem 0;
  }

  .warningEmoji {
    padding: 0 0.5rem;
    width: 2.2rem;
    height: 2.2rem;
    animation: alarm ease-in-out 1s infinite;
  }

  @keyframes alarm {
    0% {
      transform: rotate(0);
    }
    15% {
      transform: rotate(5deg);
    }
    30% {
      transform: rotate(-5deg);
    }
    45% {
      transform: rotate(4deg);
    }
    60% {
      transform: rotate(-4deg);
    }
    75% {
      transform: rotate(2deg);
    }
    85% {
      transform: rotate(-2deg);
    }
    92% {
      transform: rotate(1deg);
    }
    100% {
      transform: rotate(0);
    }
  }

  .warningBox > .flex {
    padding-bottom: 2rem;
  }
`;

export const GlowingBtn = styled.button`
  width: 12rem;
  height: 4rem;
  border: none;
  outline: none;
  color: ${colors.white};
  background: ${colors.black};
  cursor: pointer;
  position: relative;
  z-index: 0;
  border-radius: 10px;
  font-size: 2rem;
  margin: 0 calc((50% - 12rem) / 2);

  &:before {
    content: '';
    background: linear-gradient(45deg, #ff0000, #ff7300, #fffb00, #48ff00, #00ffd5, #002bff, #7a00ff, #ff00c8, #ff0000);
    position: absolute;
    top: -2px;
    left: -2px;
    background-size: 400%;
    z-index: -1;
    filter: blur(5px);
    width: calc(100% + 4px);
    height: calc(100% + 4px);
    animation: glowing 20s linear infinite;
    opacity: 0;
    transition: opacity 0.3s ease-in-out;
    border-radius: 10px;
  }

  &:active {
    color: ${colors.black};
  }

  &:active:after {
    background: transparent;
  }

  &:hover:before {
    opacity: 1;
  }

  &:after {
    z-index: -1;
    content: '';
    position: absolute;
    width: 100%;
    height: 100%;
    background: ${colors.black};
    left: 0;
    top: 0;
    border-radius: 10px;
  }

  @keyframes glowing {
    0% {
      background-position: 0 0;
    }
    50% {
      background-position: 400% 0;
    }
    100% {
      background-position: 0 0;
    }
  }
`;
