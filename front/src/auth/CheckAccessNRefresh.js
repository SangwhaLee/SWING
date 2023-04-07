import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

import { API_URL, setCookie, getCookie, delCookie } from '../config';
import { useRecoilState } from 'recoil';
import { userState } from '../recoil';

function CheckAccessNRefresh() {
  const navigate = useNavigate();
  const [user, setUser] = useRecoilState(userState);

  const [successRefresh, setSuccessRefresh] = useState(false);
  const [passAccess, setPassAccess] = useState(false);

  const checkRefreshToken = () => {
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

  const checkAccessToken = () => {
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
        } else if (res.data.message === 'fail') {
          checkRefreshToken();
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
    if (user !== '' && user !== null) {
      checkAccessToken();

      if (passAccess) {
        arguments[0]();
      }
    }
    return () => {
      if (arguments[1] === null && passAccess === false) {
        setSuccessRefresh(() => false);
      }
    };
  }, [successRefresh, passAccess]);
}

export default CheckAccessNRefresh;
