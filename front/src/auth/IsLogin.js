import { getCookie } from '.././config';

const IsLogin = () => {
  return Boolean(getCookie('accessToken'));
};

export default IsLogin;
