import styled from '@emotion/styled';
import { colors } from './ColorPalette';

export const HistoryContentContainer = styled.div`
  width: 100%;
  height: 75%;
  padding: 2rem;
  background-color: ${colors.white};
  border-radius: 1.5rem;
  box-sizing: border-box;
`;

export const HistoryContent = styled.div`
  display: flex;
  flex-direction: column;
  height: calc(100% - 2rem);

  .no-history {
    height: 100%;
    font-size: 1.5rem;
    color: ${colors.gameBlue500};
    display: flex;
    justify-content: center;
    align-items: center;
  }
`;

export const SingleHistoryList = styled.div`
  display: flex;
  width: 100%;
  height: 100%;
  align-items: center;
  padding-top: 0.5rem;
  border-bottom: 2px solid ${colors.gameBlue500};
  cursor: pointer;
  box-sizing: border-box;
  font-size: 1.2rem;
  color: ${colors.gameBlue500};

  &:hover {
    background-color: #f4f6ff;
  }

  .history-date {
    flex: 0.4;
  }

  .history-title {
    flex: 0.5;
  }

  .history-rank {
    flex: 0.1;
    text-align: end;
  }
`;

export const GameRoundNav = styled.div`
  display: flex;
  width: 100%;
  justify-content: space-between;
  height: 3.5rem;

  .left {
    width: 3rem;
    height: 3rem;
    cursor: pointer;
  }

  .right {
    width: 3rem;
    height: 3rem;
    cursor: pointer;
  }

  .roundNum {
    background-color: ${colors.gamePink200};
    color: ${colors.gameBlue500};
    font-size: 1.5rem;
    font-weight: 700;
    padding: 1rem 2rem;
    border-radius: 1rem;
    display: flex;
    align-items: center;
  }
`;

export const HistoryPictureContainer = styled.div`
  padding-top: 1rem;

  .slideContainer {
    display: flex;
    flex-wrap: wrap;
  }
`;

export const SinglePicContainer = styled.div`
  width: calc((100% - 2rem) / 3);
  // height: calc((100% - 2rem) / 2);
  background-color: ${colors.gray200};
  display: flex;
  flex-direction: column;
  margin-right: 1rem;
  margin-bottom: 0.5rem;
  box-sizing: border-box;

  &:nth-of-type(3),
  &:nth-of-type(6) {
    margin-right: 0;
  }
`;

export const Picture = styled.img`
  height: 9rem;
  object-fit: contain;
`;

export const UserInfoBox = styled.div`
  display: flex;
  align-items: center;
  width: 100%;
  height: 3.5rem;
  padding: 0 1rem;
  box-sizing: border-box;

  .profile {
    flex: 0.1;
    margin: 0 1rem 0 0.5rem;
  }

  .save-btn {
    flex: 0.2;
  }
`;

export const UserNickName = styled.div`
  flex: 0.7;
  font-size: 1.1rem;
  font-weight: 700;
  color: ${colors.gameBlue500};
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
`;
