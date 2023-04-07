import styled from '@emotion/styled/macro';
import { colors } from '../styles/ColorPalette';

export const Nav = styled.nav`
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 100%;
  box-sizing: border-box;
  padding: 1rem 10%;
  position: fixed;
  top: 0;
  z-index: 999;
`;

export const NavItem = styled.div`
  padding: ${(props) => (props.padding ? props.padding : '')};
  text-align: center;
  border-bottom: ${(props) => (props.border ? `1px solid ${props.border}` : 'none')};
  cursor: pointer;
`;

export const NavItemGroup = styled.div``;

export const NavLeaderItem = styled.h4`
  font-weight: bold;
  font-size: 24px;
  line-height: 1.4;
  color: ${colors.white};
  text-shadow: 2px 0 ${colors.gameBlue500}, 0 2px ${colors.gameBlue500}, -2px 0 ${colors.gameBlue500},
    0 -2px ${colors.gameBlue500};
  cursor: pointer;
`;

export const NavSubItems = styled.div`
  position: absolute;
  top: ${(props) => (props.top ? props.top : 0)};
  left: ${(props) => (props.left ? props.left : 0)};
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  width: 15vw;
  max-width: 200px;
  height: ${(props) => (props.hover ? '15vh' : '0')};
  border: ${(props) => (props.hover ? `2px solid ${colors.gameBlue500}` : '0')};
  background-color: ${colors.white};
  overflow: hidden;
  transition: height 0.3s;
`;
