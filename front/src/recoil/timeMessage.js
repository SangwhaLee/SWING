import { atom } from 'recoil';
import { recoilPersist } from 'recoil-persist';

const { persistAtom } = recoilPersist();

export const timeMessage = atom({
  key: 'timeMessage',
  default: null,
});
