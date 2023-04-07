import { atom } from 'recoil';
import { recoilPersist } from 'recoil-persist';

const { persistAtom } = recoilPersist();

export const timeList = atom({
  key: 'timeList',
  default: {
    userNum: null,
    userList: [],
  },
});
