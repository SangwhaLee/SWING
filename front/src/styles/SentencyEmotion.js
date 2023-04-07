import styled from "@emotion/styled";
import { colors } from "./ColorPalette";

export const SentencyWrapper = styled.div`
  display: flex;
  align-items: center;
  flex-direction: column;
  width: 100%;
  min-height: 100vh;
  padding: 0 10%;
  padding-top: 9rem;
  padding-bottom: 2rem;
  box-sizing: border-box;
  background: linear-gradient(
    to top right,
    rgba(236, 61, 89, 0.5),
    rgba(22, 86, 233, 0.5)
  );

  .sentencyContentContainer {
    width: 100%;
  }
`;

export const SentencyContentContainer = styled.div`
  display: flex;
  align-items: flex-start;
  width: 100%;
  padding-bottom: 2rem;

  .sentencyImg {
    width: 45%;
    max-height: 50vh;
    object-fit: contain;
  }

  .contentRight {
    display: flex;
    flex-direction: column;
    padding-left: 2rem;
  }
`;

export const SentencyGameNav = styled.div`
  display: flex;
  width: 100%;
  align-items: center;
  justify-content: space-between;

  .heart-container {
    display: flex;
  }

  .heart {
    color: ${colors.gamePink500};
    font-size: 1.5rem;
    margin-right: 1rem;
  }

  .shake {
    animation: shake 1s linear infinite;
  }

  .green {
    color: #4aa96c;
  }

  .yellow {
    color: #ffd93d;
  }

  .red {
    color: #fc2947;
  }

  .blue {
    color: #0014ff;
  }

  @keyframes shake {
    0% {
      transform: translate(1px, 1px) rotate(0deg);
    }
    10% {
      transform: translate(-1px, -2px) rotate(-1deg);
    }
    20% {
      transform: translate(-3px, 0px) rotate(1deg);
    }
    30% {
      transform: translate(3px, 2px) rotate(0deg);
    }
    40% {
      transform: translate(1px, -1px) rotate(1deg);
    }
    50% {
      transform: translate(-1px, 2px) rotate(-1deg);
    }
    60% {
      transform: translate(-3px, 1px) rotate(0deg);
    }
    70% {
      transform: translate(3px, 1px) rotate(-1deg);
    }
    80% {
      transform: translate(-1px, -1px) rotate(1deg);
    }
    90% {
      transform: translate(1px, 2px) rotate(0deg);
    }
    100% {
      transform: translate(1px, -2px) rotate(-1deg);
    }
  }
`;

export const WordListContainer = styled.div`
  display: flex;
  flex-wrap: wrap;
  padding-bottom: 3rem;

  .finishDot {
    color: ${colors.white};
    height: 3rem;
    font-size: 2rem;
    line-height: 5rem;
  }
`;

export const WordBox = styled.span`
  width: ${(props) => `${props.width}rem`};
  height: 3rem;
  border-bottom: 3px solid ${colors.white};
  color: ${colors.white};
  margin-right: 1.5rem;
  font-size: 2rem;
  line-height: 3rem;
  letter-spacing: ${(props) => (props.spacing > 1 ? "0.5rem" : "initial")};
  text-align: center;
`;

export const SentencyTranslationContainer = styled.div`
  padding-bottom: 1rem;
`;

export const SentencyInputContainer = styled.div`
  width: 100%;
  display: flex;
  justify-content: center;
`;

export const SentencyScoreContainer = styled.div`
  display: flex;
  padding: 2rem 0;

  .sentencyResult {
    padding-left: 2rem;
  }

  .sentencyScoreBox,
  .sentencyAnswerBox {
    padding: 2rem;
    border-radius: 1rem;
    border: 2px solid ${colors.gameBlue300};
  }

  .sentencyScoreBox {
    margin-bottom: 1rem;
  }

  .sentencyAnswerBox {
    flex-grow: 1;
  }
`;

export const RetryModalContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;

  .retryInfo {
    padding-top: 2rem;
    padding-bottom: 1rem;
    font-size: 1.5rem;
  }

  .retrySubInfo {
    color: ${colors.gray500};
    padding-bottom: 2rem;
  }

  .coupon {
    width: 10rem;
  }

  .retryBtns {
    padding-top: 3rem;
  }
`;

export const ResultSentenceWrapper = styled.div`
  display: flex;
  flex-direction: column;

  .mine,
  .quiz {
    padding-bottom: 3rem;
  }

  .mine {
    color: ${colors.gameBlue300};
  }

  .quiz {
    color: ${colors.gamePink500};
  }
`;

export const SentencyLoadingBox = styled.div`
  display: flex;
  flex-direction: column;
  width: 50vw;
  height: 50vh;
  justify-content: center;
  align-items: center;
`;
