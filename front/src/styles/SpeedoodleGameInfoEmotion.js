import styled from '@emotion/styled';
import { colors } from './ColorPalette';

export const GameInfoContainer = styled.div`
  grid-row-start: 1;
  grid-row-end: 1;
  grid-column-start: 5;
  grid-column-end: 13;
  padding: 1rem;
  background-color: ${(props) =>
    props.color ? props.color : `${colors.white}`};
  border-radius: 1.5rem;
  box-sizing: border-box;
  height: 100%;
`;

export const RoomTitle = styled.div`
  width: 100%;
  height: 10%;
  background-color: ${colors.gray200};
  border-radius: 0.75rem;
  display: flex;
  align-items: center;
  justify-content: center;
  box-sizing: border-box;
`;

export const RoomInfoContainer = styled.div`
  width: 100%;
  height: calc(90% - 1rem);
  display: grid;
  grid-template-rows: repeat(1, 1fr);
  grid-template-columns: repeat(3, 1fr);
  column-gap: 1rem;
  margin-top: 1rem;
`;

export const RoomInfo = styled.div`
  grid-row-start: 1;
  grid-row-end: 1;
  grid-column-start: 1;
  grid-column-end: 3;
`;

export const Chat = styled.div`
  position: relative;
  grid-row-start: 1;
  grid-row-end: 1;
  grid-column-start: 3;
  grid-column-end: 4;
  padding: 0.5rem;
  background-color: ${colors.gray200};
  border-radius: 1rem;
  height: calc(100% - 1rem);
  display:flex;
  flex-direction: column;
  justify-content: space-between;
  overflow: hidden;
`;

export const ChattingContainer = styled.div`
  position: absolute;
  padding: 0.5rem;
  width: 88%;
  height: 85%;
  margin-bottom: 1rem;
  display: flex;
  flex-direction: column;
  gap: 5px;
  overflow-y: auto;
  word-wrap: break-word;

  &::-webkit-scrollbar {
    width:3px;
  }
  &::-webkit-scrollbar-thumb {
    border-radius: 2px;
    background-color: ${colors.gameBlue300};
  }
  &::-webkit-scrollbar-track {
    background-color: ${colors.studyYellow100}
  }
`;

export const ChattingInputContainer = styled.div`
  display: flex;
  position: absolute;
  width: 94.5%;
  bottom: 0.5rem;
  height: 3rem;
  border-radius: 0.5rem;
  background-color: ${colors.white};
`;

export const ChatInput = styled.input`
  min-width: calc(100% - 48px);
  padding-left: 0.5rem;
  border: none;
  border-radius: 1rem;
  &:focus {
    outline: none;
  }
`;

export const GameExplain = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  align-items: center;
  width: 100%;
  height: 40%;
  margin-bottom: 4%;
  border-radius: 1rem;
  background-color: ${colors.gray200};
`;

export const GameModeContainer = styled.div`
  display: flex;
  justify-content: space-between;
  width: 100%;
  height: 30%;
  margin-bottom: 4%;
`;

export const GameMode = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  width: calc((100% - 1rem) / 2);
  height: 100%;
  padding: 1rem;
  background-color: ${(props) => props.color};
  border-radius: 1rem;
  color: ${(props) => props.fontColor};
  border: ${(props) => props.border};
  box-sizing: border-box;
  word-break: keep-all;
`;

export const BtnContainer = styled.div`
  width: 100%;
  height: 20%;
  display: flex;
  justify-content: space-between;
`;
