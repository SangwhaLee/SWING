import { H5 } from '../styles/Fonts';
import { colors } from '../styles/ColorPalette';
import { PlayerInfo, PlayerName, PlayerProfile, PlayerScore, SinglePlayerContainer } from '../styles/CommonEmotion';

function SingleRankInfo(props) {
  return (
    <>
      <SinglePlayerContainer>
        <PlayerInfo>
          <H5 color={colors.gameBlue400}>4</H5>
          <PlayerProfile
            src='https://lh3.googleusercontent.com/js_XKST6gTKYfudPTVMznmSGTKa82mINzymaOZwsad4EWv10TMh61HbO2yyvu3lz80MIfs5ZgUMZ4iYfOZ_Flpf8bcNIGD-hR4K4WKNVyQ'
            width={2.5}
            height={2.5}
            margin={0.5}
          />
          <PlayerName font={'1rem'} fontWeight={500}>
            플레이어 4
          </PlayerName>
        </PlayerInfo>
        <PlayerScore font={'20px'}>75</PlayerScore>
      </SinglePlayerContainer>
    </>
  );
}

export default SingleRankInfo;
