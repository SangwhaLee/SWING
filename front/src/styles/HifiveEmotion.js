import styled from '@emotion/styled';
import { css, keyframes } from '@emotion/react'
import { colors } from './ColorPalette';

const HifiveWrapper = styled.div`
  padding-top: 9rem;
  width: 100%;
  height: calc(100vh - 9rem);
  background-color: #4F84FF;
  display: flex;
  flex-direction: column;
  align-items: center;
  `;

const HifiveContainer = styled.div`
  margin: 0 auto;
  width: 70%;
  height: 80%;
  display: grid;
  grid-template-columns: repeat(12, 1fr);
  grid-template-rows: repeat(12, 1fr);
  column-gap: 2rem;
`;

const ProblemtoalContainer = styled.div`
  grid-column: 1/10;
  grid-row: 1/10;
`;

const GameinfoContainer = styled.div`
  width: 100%;
  display: flex;
  justify-content: space-between;

  .heart-container {
    display: flex;
    align-items: center;
  }

  .heart {
    color: ${colors.gamePink500};
    font-size: 2.1rem;
    margin-right: 1rem;
  }
`;

const AnswertotalContainer = styled.div`
  grid-column: 10/13;
  grid-row: 1/10;
`;

const AnswerContainer = styled.div`
  width: 100%;
  border: 1px solid #FFFFFF;
  background-color: #FFFFFF;
  border-radius: 0.5rem;
  height: 100%;
  display: flex;
  flex-direction: column;
  gap: 1rem;
  padding: 2rem 1rem 2rem 1rem;  
  box-sizing: border-box;

  .singleAnswer {
    display: flex;
    flex-direction: column;
    gap: 0.5rem;
  }  

`;

const bounce = keyframes`
  from, 0%, 25%, 50%, 75%, 100% to {
    transform: translate3d(0,0,0);
  }

  25%, 75% {
    transform: translate3d(-10px, 0, 0);
  }

  50% {
    transform: translate3d(10px, 0, 0);
  }

  0%, 100% {
    transform: translate3d(0,0,0);
  }
`;

const shake = keyframes`
  0% { transform: translate(-50%, -50%); }
  25% { transform: translate(-57%, -50%); }
  50% { transform: translate(-50%, -50%); }
  75% { transform: translate(-43%, -50%); }
  100% { transform: translate(-50%, -50%); }
`;

const ProblemContainer = styled.div`
  width: 100%;
  position: relative;
  border: 5px solid ${props => props.color};
  background-color: #FFFFFF;
  border-radius: 0.5rem;
  height: 100%;
  animation: ${(props) => props.vibration && css`${bounce} 0.3s ease-out` };
  box-sizing: border-box;

  .correctEmoji {
    position: absolute;
    left: 50%;
    top: 50%;
    transform: translate(-50%, -50%);
  }
`;

export const GameImage = styled.img`
  height: 90%;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  opacity: ${props => props.opacity};
  animation: ${(props) => props.vibration && css`${shake} 0.15s linear` };
  animation-i teration-count: 2;
  -webkit-user-select:none;
  -moz-user-select:none;
  -ms-user-select:none;
  user-select:none
  -webkit-user-drag: none;
  -khtml-user-drag: none;
  -moz-user-drag: none;
  -o-user-drag: none;
  user-drag: none;
  pointer-events: none;
`;

const InputContainer = styled.form`
  grid-column: 1/13;
  grid-row: 11;
  display: flex;
  gap: 1.5rem;
  padding-top: 2rem;
`;

export const HifiveModalContainer = styled.div`
  display: flex;
  flex-direction: row;
  justify-content: center;
  gap: 2.5rem;
`;

export const TemporaryRanking = styled.div`
  border-radius: 2rem;
  border-style: solid;
  border-width: 2px;
  border-color: ${(props) => props.color};
`;

export const HifiveStatistics = styled.div`
  border-radius: 2rem;
  border-style: solid;
  border-width: 2px;
  border-color: ${(props) => props.color};
  box-sizing: border-box;

  display: flex;
  flex-direction: column;
  align-items: center;

  padding: 1rem 2rem 1.5rem 2rem;

  .resultBox {
    padding-top: 2.5rem;
    display: flex;
    flex-direction: column;
    gap: 4rem;
  }

  .resultValue {
    width: ${(props) => props.width}rem;
    display: flex;
    flex-direction: row;
    justify-content: space-between
  }
`;

export { HifiveWrapper,
  HifiveContainer,
  AnswerContainer,
  ProblemContainer,
  GameinfoContainer,
  ProblemtoalContainer,
  AnswertotalContainer,
  InputContainer,
};
