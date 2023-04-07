import styled from '@emotion/styled';
import { colors } from './ColorPalette';
import gridnote from '../assets/gridnote.jpg';

export const ReviewNoteWrapper = styled.div`
  background-image: url(${gridnote});
`;

export const ReviewNoteWrapperColor = styled.div`
  display: flex;
  flex-direction: column;
  // align-items: center;
  width: 100vw;
  min-height: 100vh;
  padding: 0 10%;
  padding-top: 9rem;
  box-sizing: border-box;
  background-color: rgba(129, 147, 221, 0.1);

  .selectButton {
    display: flex;
    gap: 1.5rem;
  }

  .noteTitle {
    padding-bottom: 2rem;
  }
`;

export const WrongBox = styled.div`
  width: 100%;
  padding-top: 2rem;
  display: flex;
  flex-direction: column;

  .no-wrongs {
    height: calc(100vh - 26rem);
    display: flex;
    justify-content: center;
    align-items: center;
    font-size: 1.5rem;
    border-radius: 0.5rem;
    padding: 1rem 2rem 1rem 2rem;
    box-sizing: border-box;
    background-color: ${colors.white};
  }
`;

export const ReviewBtnContainer = styled.div`
  display: flex;
  flex-direction: row;
  justify-content: space-between;
`;

export const WrongThingBox = styled.div`
  display: flex;
  position: relative;
  margin-bottom: 1rem;
  border-radius: 0.5rem;
  background-color: ${colors.white};
  padding: 1rem 2rem 1rem 2rem;
  box-sizing: border-box;

  .contentBox {
    width: 92%;
  }

  .thingMean {
    font-size: 20px;
    display: ${(props) => props.display};
  }

  .checkBtn {
    height: auto;
    font-size: 2.5rem;
    position: absolute;
    right: 2rem;
    top: 50%;
    transform: translateY(-40%);
    cursor: pointer;
  }
`;

export const ThingMean = styled.div`
  font-size: 20px;
  margin-bottom: ${(props) => props.margin}rem;
`;

export const PaginationBox = styled.div`
  margin-top: 0.75rem;
  width: 40%;
  height: 3rem;
  align-self: center;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 1rem;
  position: absolute;
  bottom: 1.5rem;
`;

export const PageNumBox = styled.button`
  background-color: ${colors.white};
  width: 2.5rem;
  height: 2.5rem;
  border-radius: 6px;
  border: 2px solid ${colors.gray500};
  color: ${colors.gameBlue500};
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 0px;
  cursor: pointer;

  &[disabled] {
    background-color: ${colors.gray300};
    color: ${colors.gray500};
    cursor: revert;
    transform: revert;
  }

  &[aria-current] {
    background-color: ${colors.gameBlue100};
    border: 2px solid ${colors.gameBlue500};
    color: ${colors.gameBlue400};
  }
`;
