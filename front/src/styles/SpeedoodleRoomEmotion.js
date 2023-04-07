import styled from '@emotion/styled';
import { colors } from './ColorPalette';

export const SpeedoodleRoomContainer = styled.div`
  display: grid;
  grid-template-rows: repeat(1, 1fr);
  grid-template-columns: repeat(12, 1fr);
  column-gap: 2rem;
  width: 100%;
  height: 100%;
  -webkit-user-drag: none;
  -khtml-user-drag: none;
  -moz-user-drag: none;
  -o-user-drag: none;
  user-drag: none;
  -webkit-user-select:none;
  -moz-user-select:none;
  -ms-user-select:none;
  user-select:none
  pointer-events: none;
`;
