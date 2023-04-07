import { atom } from 'recoil';
import { recoilPersist } from 'recoil-persist';

const { persistAtom } = recoilPersist();

export const speedoodleGameState = atom({
  key: 'speedoodleGameState',
  default: false,
  effects_UNSTABLE: [persistAtom],
});
