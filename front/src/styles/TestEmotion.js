import styled from '@emotion/styled';
import { colors } from './ColorPalette';

export const TestWrapper = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  width: 100%;
  height: 100vh;
  padding: 0 10%;
  padding-top: 9rem;
  padding-bottom: 2rem;
  box-sizing: border-box;
`;

export const WordTestContentContainer = styled.div`
  display: flex;
  flex-direction: column;
  padding: 2rem 1rem 0 2rem;
  margin-bottom: 1rem;
  overflow-y: auto;

  /* 스크롤바 설정*/
  &::-webkit-scrollbar {
    width: 10px;
  }

  /* 스크롤바 막대 설정*/
  &::-webkit-scrollbar-thumb {
    height: 17%;
    background-color: ${colors.gray400};
    border-radius: 10px;
  }

  /* 스크롤바 뒷 배경 설정*/
  &::-webkit-scrollbar-track {
    background-color: ${colors.gray200};
  }
`;

export const SentenceTestContentContainer = styled.div`
  display: flex;
  flex-direction: column;
  width: 100%;
  justify-content: start;
  padding-top: 4rem;

  .testImg {
    width: fit-content;
    height: 25rem;
    object-fit: contain;
  }

  .testDesc {
    padding: 2rem 0;
  }

  .translation {
    padding-left: 2rem;
    font-size: 1.5rem;
    font-weight: 700;
    display: flex;
    align-items: center;
  }
`;

export const SingleWordTestContainer = styled.div`
  display: flex;
  padding-bottom: 2rem;
`;

export const WordMeaning = styled.div`
  flex-grow: 1;
  padding: 0 2rem;
  font-size: 1.5rem;
  font-weight: 700;
`;

export const SentenceTestInputContainer = styled.div`
  width: 100%;
  display: flex;
  justify-content: flex-start;
`;

export const TestContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 5rem 0;
  width: 35vw;

  .coupon {
    width: 10rem;
    padding-bottom: 1rem;
  }

  .resultItemBox {
    flex-wrap: wrap;
    padding-top: 4rem;
    justify-content: space-between;
  }

  .resultIcon {
    font-size: 2rem;
    padding: 0 1rem;
  }

  .correct {
    color: #06c258;
  }

  .wrong {
    color: #ff0000;
  }
`;

export const TestModalTitle = styled.div`
  display: flex;

  .resultEmoji {
    width: 4rem;
    height: 4rem;
    padding: 0 0.5rem;
  }
`;

export const TestFailModalContent = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: flex-start;

  .wrongAnswer,
  .rightAnswer {
    padding-left: 1rem;
  }

  .contentLine {
    padding-bottom: 1rem;
  }
`;
