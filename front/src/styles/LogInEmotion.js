import styled from '@emotion/styled';
import LoginSide from '../assets/resize_login_side.gif';

export const LogInWrapper = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  width: 100vw;
  height: 100vh;
  padding: 0 10%;
  box-sizing: border-box;
  background: linear-gradient(
    to top left,
    rgba(0, 57, 188, 1),
    rgba(253, 152, 152, 0.7)
  );
`;

export const LogInContainer = styled.div`
  display: flex;
  justify-content: space-between;
  padding: 4rem;
  width: 80%;
  height: 68vh;
  box-sizing: border-box;
  border-radius: 0.75rem;
  background-color: rgba(255, 255, 255, 0.7);
`;

export const ExpImg = styled.div`
  width: 50%;
  height: 100%;
  border-radius: 0.75rem;
  background: center / cover no-repeat url(${LoginSide});
`;

export const RightContainer = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: space-evenly;
  width: 40%;
  height: 100%;
`;

export const LogoImg = styled.div`
  display: flex;
  justify-content: center;
  width: 100%;
  height: 50%;
  box-sizing: border-box;
`;

export const LogInBtnContainer = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: end;
  box-sizing: border-box;
`;

export const LogInBtn = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  padding: 1.2rem;
  border-radius: 0.5rem;
  box-sizing: border-box;
  background-color: #f7e600;
  text-align: center;
  font-size: 1.5rem;
  cursor: pointer;
`;

export const SocialLogoImg = styled.img`
  top: 14px;
  left: 16px;
  width: 2.5rem;
  height: 2.5rem;
`;
