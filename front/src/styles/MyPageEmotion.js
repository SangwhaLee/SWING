import styled from '@emotion/styled';
import { colors } from './ColorPalette';

export const MyPageWrapper = styled.div`
  display: flex;
  align-items: center;
  flex-direction: column;
  width: 100vw;
  height: 100vh;
  padding: 0 10%;
  padding-top: 9rem;
  box-sizing: border-box;
  background-color: ${colors.studyBlue300};

  .desc {
    width: 100%;
    text-align: left;
    font-size: 1rem;
    color: ${colors.white};
    padding-bottom: 0.5rem;
  }

  .history-btn {
    width: 100%;
    justify-content: flex-end;
  }
`;

export const MyPageContentContainer = styled.div`
  display: grid;
  grid-template-rows: repeat(1, 1fr);
  grid-template-columns: repeat(12, 1fr);
  column-gap: 2rem;
  width: 100%;
  height: 75%;
  box-sizing: border-box;
`;

export const MyPageMainConatiner = styled.div`
  grid-row-start: 1;
  grid-row-end: 1;
  grid-column-start: 1;
  grid-column-end: 10;
  box-sizing: border-box;
`;

export const MyPageSideConatiner = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-between;
  padding: 1.5rem;
  grid-row-start: 1;
  grid-row-end: 1;
  grid-column-start: 10;
  grid-column-end: 13;
  background-color: ${colors.white};
  border-radius: 1rem;
  box-sizing: border-box;
`;

export const MyPageProfileConatiner = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  height: 50%;
  box-sizing: border-box;
`;

export const MyPageProfile = styled.img`
  width: 10vw;
  height: 10vw;
  border-radius: 50%;
  object-fit: cover;
`;

export const FilePreview = styled.img`
  width: 10rem;
  height: 10rem;
  border-radius: 50%;
  filter: brightness(70%);
  object-fit: cover;
  cursor: pointer;

  & ~ .edit {
    position: absolute;
    top: 13.5rem;
    font-size: 2rem;
    color: ${colors.white};
    cursor: pointer;
  }
`;

export const MyPageProfileNickname = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  width: 100%;
  padding-top: 1rem;

  .nickname {
    width: 15vw;
    text-align: center;
    font-size: 1.5rem;
    font-weight: 700;
    color: ${colors.black};
    padding-bottom: 1rem;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
  }
`;

export const MyPageProfileCoupon = styled.div`
  display: flex;
  flex-wrap: wrap;
  width: 50%;
  justify-content: space-around;
  align-items: center;
`;

export const CouponImg = styled.img`
  width: ${(props) => props.width}rem;
`;

export const MyPageIntroConatiner = styled.div`
  display: flex;
  position: relative;
  align-items: center;
  width: 100%;
  padding: 1rem 1.5rem;
  background-color: ${colors.white};
  border-radius: 1rem;
  box-sizing: border-box;
  overflow: hidden;

  .swingImg {
    width: 8rem;
    position: absolute;
    top: -10%;
    right: 15%;

    animation: swing ease-in-out 2s infinite alternate;
    transform-origin: 100% 0%;
  }

  @keyframes swing {
    0% {
      transform: rotate(5deg);
    }
    100% {
      transform: rotate(-65deg);
    }
  }

  .swing-bold {
    font-weight: 700;
    color: ${colors.studyBlue300};
  }

  h3 {
    color: ${colors.gameYellow300};
  }
`;

export const MyPageHistoryConatiner = styled.div`
  display: flex;
  flex-direction: column;
  width: 100%;
  height: calc(75% - 4rem);
  padding: 1rem 1.5rem;
  margin-top: 2rem;
  background-color: ${colors.white};
  border-radius: 1rem;
  box-sizing: border-box;

  .more-list-nav {
    width: 100%;
    display: flex;
    justify-content: flex-end;
    padding-top: 1rem;
  }

  .more-list-nav h6 {
    cursor: pointer;
    color: ${colors.studyBlue500};

    &:hover {
      font-weight: 900;
    }
  }
`;

export const HistoryHeader = styled.div`
  display: flex;
  justify-content: space-between;
  padding-bottom: 0.5rem;
  border-bottom: ${(props) => (props.border ? `2px solid ${colors.gameBlue500}` : '')};
  box-sizing: border-box;
  font-size: 1.2rem;
  font-weight: 700;
  color: ${colors.gameBlue500};

  .date {
    flex: 0.4;
  }

  .roomname {
    flex: 0.5;
  }

  .rank {
    flex: 0.1;
    text-align: end;
  }
`;
// export const MyPageHistoryList = styled.div`
//   display: flex;
//   justify-content: space-between;
//   padding-top: 0.5rem;
//   border-bottom: 2px solid ${colors.gameBlue500};
//   cursor: pointer;
//   box-sizing: border-box;

//   &:hover {
//     background-color: #f4f6ff;
//   }
// `;

export const NickNameEditBox = styled.div`
  padding: 4rem 5rem 0 5rem;
  width: 32rem;
  height: 10rem;

  .inputBox {
    flex-grow: 1;
    height: 3rem;
    font-size: 1rem;
    background-color: ${colors.gray100};
    border: none;
    border-radius: 0.5rem;
    padding-left: 1rem;
  }

  .msg {
    padding-top: 0.5rem;
    flex-grow: 1;
    text-align: left;
  }

  .msg > .allowed {
    color: ${colors.studyBlue300};
  }

  .msg > .denied {
    color: #ed4337;
  }
`;

export const EditBtnBox = styled.div`
  display: flex;
  justify-content: center;
`;
