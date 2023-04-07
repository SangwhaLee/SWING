import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

import { useRecoilState } from 'recoil';
import { userState } from '../recoil';

import {
  LogInWrapper,
  LogInContainer,
  ExpImg,
  RightContainer,
  LogoImg,
  LogInBtnContainer,
  LogInBtn,
  SocialLogoImg,
} from '../styles/LogInEmotion';
import { RoundLogo } from '../styles/CommonEmotion';
import { colors } from '../styles/ColorPalette';
import { H5 } from '../styles/Fonts';

import Kakao from '../assets/kakaotalk_icon.png';
import { API_URL, setCookie, getCookie } from '../config';

function LogIn() {
  const navigate = useNavigate();
  const [user, setUser] = useRecoilState(userState);
  const onClickLogo = () => {
    navigate('/');
  };

  const kakaoLogin = async (e) => {
    window.Kakao.Auth.login({
      scope: 'profile_nickname',
      success: getProfile,
    });
  };

  const getProfile = () => {
    window.Kakao.API.request({
      url: '/v2/user/me',
      success: (res) => {
        const userId = 'kakao' + res.id;
        const userNickname = res.properties.nickname;

        // 프론트에서 카카오 로그인 처리하고 백으로 POST /user/socialLogin url로
        // userDto{ userId, nickname }만 넘겨주기
        axios
          .post(`${API_URL}/user/socialLogin/`, {
            nickname: userNickname,
            userId: userId,
          })
          .then((res) => {
            if (res.status === 200) {
              const userSave = {
                userId: res.data.userId,
                nickname: res.data.nickname,
                profileImageUrl: res.data.profileImageUrl,
              };

              setUser(userSave);
              setCookie('accessToken', res.data['access-token'], 1);
              setCookie('refreshToken', res.data['refresh-token'], 1);
              navigate('/');
            }
          })
          .catch((err) => {
            console.error(err);
          });
      },
    });
  };

  return (
    <>
      <LogInWrapper>
        <LogInContainer>
          <ExpImg />
          <RightContainer>
            <LogoImg>
              <RoundLogo alt='logo' onClick={onClickLogo} size='70%' />
            </LogoImg>
            <LogInBtn onClick={kakaoLogin}>
              <SocialLogoImg src={Kakao} alt='kakao' />
              <H5 align='center' margin='0 0 0 1rem'>
                카카오로 시작하기
              </H5>
            </LogInBtn>
          </RightContainer>
        </LogInContainer>
      </LogInWrapper>
    </>
  );
}

export default LogIn;
