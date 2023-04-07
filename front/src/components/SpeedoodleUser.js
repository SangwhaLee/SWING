import React, { useEffect, useState } from 'react';

import { UserInfoContainer, UserInfo } from '../styles/SpeedoodleUserEmotion';
import { PlayerInfo, PlayerProfile, SinglePlayerLine } from '../styles/CommonEmotion';
import { P1 } from '../styles/Fonts';
import { colors } from '../styles/ColorPalette';
import { AwardFill } from 'react-bootstrap-icons';

function SpeedoodleUser(props) {
  // props에 axios주소값 넘겨주고 거기서 받아온 user를 map으로 띄워줌

  // props값 없어서 더미 넘겨줌

  const gameUsers = props.data;
  const userInfo = gameUsers?.map((user, idx) => {
    return (
      <UserInfo key={user.userId} border={idx !== gameUsers.length - 1 ? `${colors.gameBlue500}` : false}>
        <PlayerInfo>
          <PlayerProfile src={user.profileImageUrl} alt='user image' width='2.5' height='2.5' />
          {props.leader === user.nickname ? (
            <div style={{ display: 'flex', alignItems: 'center' }}>
              <AwardFill
                style={{
                  marginLeft: '1rem',
                  color: `${colors.gamePink500}`,
                  fontSize: '24px',
                }}
              />
              <P1 margin='0 0 0 1rem' color={colors.gamePink500}>
                {user.nickname}
              </P1>
            </div>
          ) : (
            <P1 margin='0 0 0 1rem' color={colors.gameBlue500}>
              {user.nickname}
            </P1>
          )}
        </PlayerInfo>
        {user.time && <P1>{user.time}</P1>}
      </UserInfo>
    );
  });
  return (
    <>
      <UserInfoContainer>{userInfo}</UserInfoContainer>
    </>
  );
}

export default SpeedoodleUser;
