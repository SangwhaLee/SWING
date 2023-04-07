import styled from '@emotion/styled';
import roundLogo from '../assets/swing_round_logo.png';
import { colors } from './ColorPalette';

export const RoundLogo = styled.div`
  width: ${(props) => props.size};
  height: ${(props) => props.size};
  background: center / contain no-repeat url(${roundLogo});
  box-sizing: border-box;
  cursor: pointer;
`;

export const CommonInput = styled.input`
  width: ${(props) => props.width};
  max-width: ${(props) => props.maxWidth};
  min-width: ${(props) => props.minWidth};
  height: ${(props) => props.height}px;
  border-radius: ${(props) => props.font / 2}rem;
  border: ${(props) => (props.border ? props.border : 'none')};
  font-size: ${(props) => props.font}rem;
  background: white;
  padding: ${(props) => props.padding};
  box-sizing: border-box;
  flex-grow: ${(props) => props.flexGrow};
  &::-webkit-outer-spin-button,
  &::-webkit-inner-spin-button {
    -webkit-appearance: none;
    margin: 0;
  }
`;

export const CommonBtn = styled.button`
  width: ${(props) => props.width};
  min-width: ${(props) => props.minWidth};
  height: ${(props) => props.height}px;
  border-radius: ${(props) => props.font / 2}rem;
  border: ${(props) => (props.border ? props.border : 'none')};
  color: ${(props) => props.fontColor};
  background-color: ${(props) => props.color};
  font-size: ${(props) => props.font}rem;
  font-weight: ${(props) => props.fontWeight};
  cursor: pointer;
  -webkit-transition-duration: 0.5s;
  &:hover {
    background-color: ${(props) => props.hoverColor};
  }
  padding: ${(props) => props.padding};
  margin: ${(props) => props.margin};
  box-shadow: ${(props) => (props.shadow ? props.shadow : 'none')};
  box-sizing: border-box;
  tabindex: ${(props) => props.tabIndex};

  &[disabled] {
    background-color: ${colors.studyBlue300};
    border: none;
    color: ${colors.white};
  }
`;

export const GameTitle = styled.div`
  padding-top: 1rem;
  padding-bottom: 1.5rem;

  @media screen and (max-width: 1000px) {
    .historyName {
      font-size: 2.5rem !important;
    }
  }

  @media screen and (max-width: 830px) {
    .historyName {
      font-size: 2rem !important;
    }
  }

  @media screen and (max-width: 660px) {
    .historyName {
      font-size: 1.8rem !important;
    }
  }
`;

export const CommonModalBackdrop = styled.div`
  background-color: rgba(0, 0, 0, 0.4);
  width: 100vw;
  height: 100vh;
  top: 0%;
  position: absolute;
`;

export const CommonModalView = styled.div`
  width: ${(props) => props.width};
  height: ${(props) => props.height};
  z-index: 1000;

  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);

  background-color: white;
  border-radius: ${(props) => (props.font ? props.font / 2 : 0.5)}rem;
  box-sizing: border-box;
  padding: 0px 6rem 0px 6rem;
`;

export const ModalBackground = styled.div`
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.4);
  display: flex;
  justify-content: center;
  align-items: center;
  animation: opac 0.8s;
  z-index: 1000;

  @keyframes opac {
    from {
      opacity: 0;
    }
    to {
      opacity: 1;
    }
  }
`;

export const ModalBody = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  min-width: 50%;
  position: absolute;
  padding: 3rem 5rem;
  text-align: center;
  background-color: ${colors.white};
  border-radius: 1rem;
  z-index: 1001;
`;

export const LeaderBoardWrapper = styled.div`
  min-width: 16.5rem;
  display: flex;
  flex-direction: column;
  background-color: #abb5db;
  border-radius: 1rem;

  .leaderboardTitle {
    font-family: 'B612', sans-serif;
    font-size: 1.5rem;
    font-weight: 700;
    color: ${colors.white};
  }
`;

export const RankerContainer = styled.div`
  display: flex;
  flex-direction: column;
  border-radius: 1rem;
  padding: 1rem 0 0.5rem 0;
  background-color: ${colors.studyBlue300};

  .rankersList {
    display: flex;
    justify-content: center;
    align-items: end;
  }
`;

export const SingleRankerContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
`;

export const RankListContainer = styled.div`
  display: flex;
  flex-direction: column;
  padding: 0.5rem;
`;

export const MyRankContainer = styled.div`
  border-radius: 1rem;
  padding: 0.5rem;
  background-color: ${colors.studyBlue300};
`;

export const PlayerProfile = styled.img`
  width: ${(props) => props.width}rem;
  max-width: ${(props) => props.maxWidth}rem;
  height: ${(props) => props.height}rem;
  border-radius: 50%;
  margin: ${(props) => props.margin}rem;
  object-fit: cover;
  cursor: pointer;
`;

export const PlayerMedal = styled.img`
  width: ${(props) => props.width}rem;
  height: ${(props) => props.height}rem;
`;

export const PlayerName = styled.div`
  width: ${(props) => props.width}rem;
  font-size: ${(props) => props.font};
  font-weight: ${(props) => props.fontWeight};
  color: ${(props) => props.fontColor};
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
`;

export const PlayerScore = styled.div`
  font-size: ${(props) => props.font};
  font-weight: 700;
  color: ${colors.gameYellow300};
`;

export const SinglePlayerContainer = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 3rem;
  background-color: #ebeef4;
  border-radius: 10px;
  padding: 0 1rem;
  margin: ${(props) => props.margin};

  &:last-child {
    margin-bottom: 0;
  }
`;

export const PlayerInfo = styled.div`
  display: flex;
  align-items: center;
  flex-grow: 1;

  .profile {
    margin: 0.5rem 0.7rem 0.5rem 0;
  }

  .nickname {
    text-align: left;
  }
`;

export const SideLeaderBoardBackground = styled.div`
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.4);
  display: flex;
  justify-content: center;
  align-items: center;
  animation: opac 0.8s;
  z-index: 1000;

  @keyframes opac {
    from {
      opacity: 0;
    }
    to {
      opacity: 1;
    }
  }
`;

export const SideLeaderBoardWrapper = styled.div`
  width: 22.5rem;
  display: flex;
  flex-direction: column;
  position: absolute;
  left: 0;

  background-color: ${colors.black};
  border-radius: 0 1rem 1rem 0;
  padding: 3rem 1rem 1rem;

  .title {
    position: absolute;
    top: -1.5rem;
    width: calc(100% - 2rem);

    text-align: center;
    font-family: 'B612', sans-serif;
    font-size: 2.5rem;
    font-weight: 700;
    color: ${colors.white};
  }
`;

export const SideLeaderBoardSingleBox = styled.div`
  display: flex;
  padding: 0.5rem 1rem;
  align-items: center;
  margin-bottom: 0.5rem;

  border-radius: 1rem;
  background-color: ${colors.gray100};

  .rank {
    width: 3.5rem;
    font-weight: 700;
    font-size: 1.5rem;
  }

  .nickname {
    padding-left: 1rem;
    flex-grow: 1;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
  }

  .score {
    font-weight: 700;
    font-size: 1.5rem;
    color: #ffb800;
    padding-left: 1rem;
  }
`;
