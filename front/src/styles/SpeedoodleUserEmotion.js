import styled from '@emotion/styled';
import { colors } from './ColorPalette';

export const UserInfoContainer = styled.div`
  grid-row-start: 1;
  grid-row-end: 1;
  grid-column-start: 1;
  grid-column-end: 5;
  display: grid;
  grid-template-rows: repeat(6, 1fr);
  grid-template-columns: repeat(1, 1fr);
  padding: 0.5rem 1rem;
  background-color: ${colors.white};
  border-radius: 1.5rem;
  box-sizing: border-box;
`;

export const UserInfo = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-between;
  border-bottom: 2px solid ${(props) => (props.border ? props.border : 'none')};
`;
