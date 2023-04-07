import {
  LeaderBoardWrapper,
  MyRankContainer,
  PlayerInfo,
  PlayerMedal,
  PlayerName,
  PlayerProfile,
  PlayerScore,
  RankerContainer,
  RankListContainer,
  SinglePlayerContainer,
  SingleRankerContainer,
} from '../styles/CommonEmotion';
import { colors } from '../styles/ColorPalette';
import { H5 } from '../styles/Fonts';

import firstMedal from '../assets/1st-medal.svg';
import secondMedal from '../assets/2nd-medal.svg';
import thirdMedal from '../assets/3rd-medal.svg';

function LeaderBoard(ranks, mine) {
  let myRankInfo = {};
  let topRankers = [];
  let subRankers = [];
  if (ranks.others !== undefined) {
    myRankInfo = ranks.mine;
    topRankers = ranks.others.slice(0, 3);
    subRankers = ranks.others.slice(3, 7);
  }

  return (
    <>
      <LeaderBoardWrapper>
        <RankerContainer>
          <div className='leaderboardTitle'>LEADERBOARD</div>
          {topRankers.length !== 0 ? (
            <div className='rankersList'>
              <SingleRankerContainer>
                <PlayerProfile src={topRankers[1].profileImageUrl} width={4} height={4} margin='0 0 -0.3rem 0' />
                <PlayerMedal src={secondMedal} width={2} height={2} />
                <PlayerName width={4} fontColor={colors.white} font={'13px'}>
                  {topRankers[1].nickname}
                </PlayerName>
                <PlayerScore font={'1rem'}>{topRankers[1].score}</PlayerScore>
              </SingleRankerContainer>
              <SingleRankerContainer>
                <PlayerProfile src={topRankers[0].profileImageUrl} width={6} height={6} margin='0 0 -0.3rem 0' />
                <PlayerMedal src={firstMedal} width={2.5} height={2.5} />
                <PlayerName width={4} fontColor={colors.white} font={'16px'} fontWeight={700}>
                  {topRankers[0].nickname}
                </PlayerName>
                <PlayerScore font={'20px'}>{topRankers[0].score}</PlayerScore>
              </SingleRankerContainer>
              <SingleRankerContainer>
                <PlayerProfile src={topRankers[2].profileImageUrl} width={4} height={4} margin='0 0 -0.3rem 0' />
                <PlayerMedal src={thirdMedal} width={2} height={2} />
                <PlayerName width={4} fontColor={colors.white} font={'13px'}>
                  {topRankers[2].nickname}
                </PlayerName>
                <PlayerScore font={'1rem'}>{topRankers[2].score}</PlayerScore>
              </SingleRankerContainer>
            </div>
          ) : null}
        </RankerContainer>
        <RankListContainer>
          {subRankers &&
            subRankers.map((subRanker, idx) => {
              return (
                <SinglePlayerContainer margin={'0 0 0.5rem 0'} key={idx}>
                  <PlayerInfo>
                    <H5 color={colors.gameBlue400} padding='0 0.7rem 0 0'>
                      {idx + 4}th
                    </H5>
                    <PlayerProfile src={subRanker.profileImageUrl} width={2.5} height={2.5} className='profile' />
                    <PlayerName width={7.5} font={'1rem'} fontWeight={500} className='nickname'>
                      {subRanker.nickname}
                    </PlayerName>
                  </PlayerInfo>
                  <PlayerScore font={'20px'}>{subRanker.score}</PlayerScore>
                </SinglePlayerContainer>
              );
            })}
        </RankListContainer>
        <MyRankContainer>
          <SinglePlayerContainer>
            <PlayerInfo>
              <H5 color={colors.gamePink500} padding='0 1rem 0 0'>
                Me
              </H5>
              <PlayerProfile src={myRankInfo.profileImageUrl} width={2.5} height={2.5} className='profile' />
              <PlayerName width={7.5} font={'1rem'} fontWeight={500} className='nickname'>
                {myRankInfo.nickname}
              </PlayerName>
            </PlayerInfo>
            <PlayerScore font={'20px'}>{myRankInfo.score}</PlayerScore>
          </SinglePlayerContainer>
        </MyRankContainer>
      </LeaderBoardWrapper>
    </>
  );
}

export default LeaderBoard;
