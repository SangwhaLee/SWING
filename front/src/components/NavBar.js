import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import { userState, speedoodleGameState } from '../recoil';
import { useRecoilState } from 'recoil';
import { API_URL, setCookie, getCookie, delCookie } from '.././config';

import { RoundLogo, PlayerProfile } from '../styles/CommonEmotion';
import {
  Nav,
  NavItemGroup,
  NavItem,
  NavSubItems,
  NavLeaderItem,
} from '../styles/NavEmotion';
import { H4, H6 } from '../styles/Fonts';
import { colors } from '../styles/ColorPalette';
import { BasicProfile } from '../config';
import IsLogin from '../auth/IsLogin';
import CheckAccessNRefresh from '../auth/CheckAccessNRefresh';

function NavBar() {
  const navigate = useNavigate();
  const [isLogin, setIsLogin] = useState(false);
  const [hoverGame, setHoverGame] = useState(false);
  const [hoverProfile, setHoverProfile] = useState(false);
  const [user, setUser] = useRecoilState(userState);
  const [isGameStart, setIsGameStart] = useRecoilState(speedoodleGameState);

  const [successRefresh, setSuccessRefresh] = useState(false);
  const [passAccess, setPassAccess] = useState(false);

  const checkRefreshToken = (url) => {
    axios
      .post(
        `${API_URL}/user/refresh`,
        {
          userId: user.userId,
        },
        {
          headers: {
            'Refresh-Token': getCookie('refreshToken'),
          },
        }
      )
      .then((res) => {
        if (res.status === 200) {
          setCookie('accessToken', res.data['access-token'], 1);
          setSuccessRefresh(() => true);
          navigate(url);
        } else if (res.status === 202) {
          delCookie('accessToken');
          delCookie('refreshToken');
          setUser(null);
          navigate('/login');
        }
      })
      .catch((err) => {
        console.error(err);
      });
  };

  const checkAccessToken = (url) => {
    axios
      .post(
        `${API_URL}/user/check`,
        {},
        {
          headers: {
            'Access-Token': getCookie('accessToken'),
          },
        }
      )
      .then((res) => {
        if (res.data.message === 'success') {
          setPassAccess(() => true);
          navigate(url);
        } else if (res.data.message === 'fail') {
          checkRefreshToken(url);
          if (successRefresh) {
            setPassAccess(() => true);
          }
        }
      })
      .catch((err) => {
        console.error(err);
      });
  };

  useEffect(() => {
    if (user !== null) {
      setIsLogin(IsLogin());
    } else {
      setIsLogin(() => false);
    }
  }, [user]);

  const onClickLogo = () => {
    const currentUrl = window.location.href;
    if (currentUrl.indexOf('speedoodle/room') !== -1) {
      const roomUrl = new URL(window.location.href).pathname.split('/');
      const lengthUrl = roomUrl.length;
      const roomId = roomUrl[lengthUrl - 1];
      setIsGameStart(false);
      axios
        .delete(`${API_URL}/doodle/room/leave/${roomId}/${user.userId}`)
        .then((res) => {
          if (res.status === 200) {
            console.log('방퇴장합니다!');
          }
        })
        .catch((err) => console.error(err));
    }
    navigate('/');
  };

  const onClickSentency = () => {
    if (isLogin) {
      checkAccessToken('/sentency');
    } else {
      alert('로그인이 필요한 서비스 입니다.');
      navigate('/login');
    }
  };

  const onClickHifive = () => {
    if (isLogin) {
      checkAccessToken('/hi-five');
    } else {
      alert('로그인이 필요한 서비스 입니다.');
      navigate('/login');
    }
  };
  const onClickSpeedoodle = () => {
    if (isLogin) {
      checkAccessToken('/speedoodle');
    } else {
      alert('로그인이 필요한 서비스 입니다.');
      navigate('/login');
    }
  };
  const onClickLogIn = () => {
    navigate('/login');
  };
  const onClickMyPage = () => {
    navigate('/my-page');
  };

  const onClickReviewNote = () => {
    navigate('/review-note');
  };

  const Logout = async () => {
    await axios
      .get(`${API_URL}/user/logout/${user.userId}`, {
        headers: {
          'Access-Token': getCookie('accessToken'),
        },
      })
      .then((res) => {
        if (res.status === 200) {
          delCookie('accessToken');
          delCookie('refreshToken');
          setUser(null);
          setIsLogin(false);
          navigate('/');
        }
      })
      .catch((e) => console.error(e));
  };

  return (
    <Nav>
      <NavItemGroup onMouseLeave={() => setHoverGame(() => false)}>
        <NavLeaderItem
          onMouseEnter={() => {
            setHoverGame((prev) => true);
          }}
        >
          GAME
        </NavLeaderItem>
        <NavSubItems hover={hoverGame} top='70%' left='10%'>
          <NavItem onClick={onClickSentency}>
            <H4
              color={colors.white}
              outlineWeight='2'
              outline={colors.gameBlue500}
            >
              Sentency
            </H4>
          </NavItem>
          <NavItem onClick={onClickHifive}>
            <H4
              color={colors.white}
              outlineWeight='2'
              outline={colors.gameBlue500}
            >
              Hi-Five
            </H4>
          </NavItem>
          <NavItem onClick={onClickSpeedoodle}>
            <H4
              color={colors.white}
              outlineWeight='2'
              outline={colors.gameBlue500}
            >
              Speedoodle
            </H4>
          </NavItem>
        </NavSubItems>
      </NavItemGroup>

      <RoundLogo alt='logo' onClick={onClickLogo} size='7rem' />
      {user !== '' && user !== null ? (
        <>
          <NavItemGroup
            onMouseLeave={() => setHoverProfile(() => false)}
            onMouseEnter={() => setHoverProfile(() => true)}
          >
            <PlayerProfile
              src={user.profileImageUrl}
              width='3'
              height='3'
              alt='user profile image'
              style={{ cursor: 'pointer' }}
              onClick={() => setHoverProfile(() => true)}
            />
            <NavSubItems hover={hoverProfile} top='70%' left='80%'>
              <NavItem onClick={onClickMyPage} padding='0.5rem 2rem'>
                <H6>MyPage</H6>
              </NavItem>
              <div
                style={{
                  width: '80%',
                  border: `1px solid ${colors.gray500}`,
                }}
              ></div>
              <NavItem onClick={onClickReviewNote} padding='0.5rem 2rem'>
                <H6>ReviewNote</H6>
              </NavItem>
              <div
                style={{
                  width: '80%',
                  border: `1px solid ${colors.gray500}`,
                }}
              ></div>
              <NavItem onClick={() => Logout()} padding='0.5rem 2rem'>
                <H6>LogOut</H6>
              </NavItem>
            </NavSubItems>
          </NavItemGroup>
        </>
      ) : (
        <NavItem onClick={onClickLogIn}>
          <H4
            color={colors.white}
            outlineWeight='2'
            outline={colors.gameBlue500}
          >
            LogIn
          </H4>
        </NavItem>
      )}
    </Nav>
  );
}

export default NavBar;
