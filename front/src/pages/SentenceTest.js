import { useEffect, useRef, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import { API_URL, getCookie } from '../config';

import { H1, H2, H4 } from '../styles/Fonts';
import {
  SentenceTestContentContainer,
  SentenceTestInputContainer,
  TestContainer,
  TestFailModalContent,
  TestModalTitle,
  TestWrapper,
} from '../styles/TestEmotion';
import Coupon from '../assets/coupon.png';
import { CommonBtn, CommonInput } from '../styles/CommonEmotion';
import { colors } from '../styles/ColorPalette';
import ModalBasic from '../components/ModalBasic';
import { useRecoilState } from 'recoil';
import { userState } from '../recoil';

function SentenceTest() {
  const navigate = useNavigate();

  const [successModalShow, setSuccessModalShow] = useState(false);
  const [failModalShow, setFailModalShow] = useState(false);

  const [answer, setAnswer] = useState('');
  const [sentence, setSentence] = useState('');
  const sentenceInput = useRef();
  const [coupon, setCoupon] = useState();

  const [user, setUser] = useRecoilState(userState);

  const getRandomSentence = async () => {
    await axios
      .get(`${API_URL}/note/sentence/${user.userId}/1`, {
        headers: {
          'Access-Token': getCookie('accessToken'),
        },
      })
      .then((res) => {
        console.log(res);
        if (res.data.sentenceNoteList.length === 0) {
          alert('최소 1개의 문장이 오답노트에 존재해야만 테스트를 응시할 수 있습니다!');
          navigate('/review-note', { state: 2 });
          return;
        }
        setAnswer(res.data.sentenceNoteList[0]);
        setCoupon(res.data.coupon);
        // setTranslation(res.data.sentenceNoteList[0].meaningKr);
        // setImgURL(res.data.sentenceNoteList[0].sentenceImageUrl);
      });
  };

  const onChangeInput = (e) => {
    setSentence(e.target.value.trim());
  };

  const updateCoupon = (curCoupon) => {
    axios
      .put(`${API_URL}/user/coupon/${user.userId}/${curCoupon + 1}`, null, {
        headers: {
          'Access-Token': getCookie('accessToken'),
        },
      })
      .then((res) => {
        console.log(res);
        console.log('쿠폰 획득 +1 완료!');
      });
  };

  const handleSubmit = () => {
    let tmpSentence = sentence;
    if (sentence.slice(-1) !== '.') {
      tmpSentence += '.';
      setSentence(tmpSentence); // 온점 추가해주기
      console.log(tmpSentence);
    }
    if (tmpSentence.toLowerCase() === answer.content.toLowerCase()) {
      // 쿠폰 지급
      updateCoupon(coupon);
      // 맞춘 문장은 오답노트에서 삭제
      axios
        .delete(`${API_URL}/note/sentence/${answer.sentenceNoteId}`, {
          headers: {
            'Access-Token': getCookie('accessToken'),
          },
        })
        .then((res) => {
          console.log(res);
        })
        .catch((err) => {});
      setSuccessModalShow(true);
    } else {
      if (sentence === '' || sentence === '.') {
        alert('답안을 입력해주세요.');
        return;
      }
      setFailModalShow(true);
    }
  };

  const handleContinue = () => {
    // 다음 문제 불러오기
    getRandomSentence();

    setSuccessModalShow(false);
    setFailModalShow(false);
    // setAnswer 새로 해주기
    sentenceInput.current.value = '';
    setSentence('');
    sentenceInput.current.focus();
  };

  const handleExit = () => {
    setSuccessModalShow(false);
    setFailModalShow(false);
    navigate('/review-note', { state: 2 }); // 종료 누르면 오답노트로 navigate
  };

  const onEnterSubmit = (e) => {
    if (e.key === 'Enter') {
      handleSubmit();
    }
  };

  useEffect(() => {
    // 랜덤 오답 문장 불러오기
    getRandomSentence();

    setTimeout(() => {
      sentenceInput.current.focus();
    }, 500);
  }, []);

  return (
    <>
      {/* 복습 테스트 성공 모달 */}
      <ModalBasic modalShow={successModalShow} setModalShow={setSuccessModalShow}>
        <TestModalTitle>
          <object
            type='image/svg+xml'
            data='data:image/svg+xml,%3Csvg xmlns="http%3A%2F%2Fwww.w3.org%2F2000%2Fsvg" width="24" height="24" viewBox="0 0 24 24"%3E%3Cpath fill="%23fe5671" fill-opacity="0" d="M12 20L20.5 11V7L17 5.5L12 7L7 5.5L3.5 7V11L12 20Z"%3E%3Canimate fill="freeze" attributeName="fill-opacity" begin="0.5s" dur="0.5s" values="0%3B1"%2F%3E%3C%2Fpath%3E%3Cpath fill="none" stroke="%23fe5671" stroke-dasharray="30" stroke-dashoffset="30" stroke-linecap="round" stroke-width="2" d="M12 8C12 8 12 8 12.7578 7C13.6343 5.84335 14.9398 5 16.5 5C18.9853 5 21 7.01472 21 9.5C21 10.4251 20.7209 11.285 20.2422 12C19.435 13.206 12 21 12 21M12 8C12 8 12 8 11.2422 7C10.3657 5.84335 9.06021 5 7.5 5C5.01472 5 3 7.01472 3 9.5C3 10.4251 3.27914 11.285 3.75777 12C4.56504 13.206 12 21 12 21"%3E%3Canimate fill="freeze" attributeName="stroke-dashoffset" dur="0.5s" values="30%3B0"%2F%3E%3C%2Fpath%3E%3C%2Fsvg%3E'
            className='resultEmoji'
          ></object>
          <H1 color={colors.gamePink400}>SUCCESS</H1>
          <object
            type='image/svg+xml'
            data='data:image/svg+xml,%3Csvg xmlns="http%3A%2F%2Fwww.w3.org%2F2000%2Fsvg" width="24" height="24" viewBox="0 0 24 24"%3E%3Cpath fill="%23fe5671" fill-opacity="0" d="M12 20L20.5 11V7L17 5.5L12 7L7 5.5L3.5 7V11L12 20Z"%3E%3Canimate fill="freeze" attributeName="fill-opacity" begin="0.5s" dur="0.5s" values="0%3B1"%2F%3E%3C%2Fpath%3E%3Cpath fill="none" stroke="%23fe5671" stroke-dasharray="30" stroke-dashoffset="30" stroke-linecap="round" stroke-width="2" d="M12 8C12 8 12 8 12.7578 7C13.6343 5.84335 14.9398 5 16.5 5C18.9853 5 21 7.01472 21 9.5C21 10.4251 20.7209 11.285 20.2422 12C19.435 13.206 12 21 12 21M12 8C12 8 12 8 11.2422 7C10.3657 5.84335 9.06021 5 7.5 5C5.01472 5 3 7.01472 3 9.5C3 10.4251 3.27914 11.285 3.75777 12C4.56504 13.206 12 21 12 21"%3E%3Canimate fill="freeze" attributeName="stroke-dashoffset" dur="0.5s" values="30%3B0"%2F%3E%3C%2Fpath%3E%3C%2Fsvg%3E'
            className='resultEmoji'
          ></object>
        </TestModalTitle>
        <TestContainer>
          <img src={Coupon} className='coupon' alt='coupon' />
          <H4>재도전 쿠폰 적립 +1</H4>
        </TestContainer>
        <div className='flex'>
          <CommonBtn
            padding={'1rem 3rem'}
            font={1.5}
            color={colors.gameBlue300}
            hoverColor={colors.gameBlue400}
            fontColor={colors.white}
            border={'none'}
            margin={'0 4rem 0 0'}
            onClick={handleContinue}
          >
            계속
          </CommonBtn>
          <CommonBtn
            padding={'1rem 3rem'}
            font={1.5}
            color={colors.gamePink400}
            hoverColor={colors.gamePink500}
            fontColor={colors.white}
            border={'none'}
            onClick={handleExit}
          >
            종료
          </CommonBtn>
        </div>
      </ModalBasic>
      {/* 복습 테스트 실패 모달 */}
      <ModalBasic modalShow={failModalShow} setModalShow={setFailModalShow}>
        <TestModalTitle>
          <object
            type='image/svg+xml'
            data='data:image/svg+xml,%3Csvg xmlns="http%3A%2F%2Fwww.w3.org%2F2000%2Fsvg" width="24" height="24" viewBox="0 0 24 24"%3E%3Cg stroke="%23fe5671" stroke-linecap="round" stroke-width="2"%3E%3Cpath fill="%23fe5671" fill-opacity="0" stroke-dasharray="60" stroke-dashoffset="60" d="M12 3C16.9706 3 21 7.02944 21 12C21 16.9706 16.9706 21 12 21C7.02944 21 3 16.9706 3 12C3 7.02944 7.02944 3 12 3Z"%3E%3Canimate fill="freeze" attributeName="stroke-dashoffset" dur="0.5s" values="60%3B0"%2F%3E%3Canimate fill="freeze" attributeName="fill-opacity" begin="0.8s" dur="0.15s" values="0%3B0.3"%2F%3E%3C%2Fpath%3E%3Cpath fill="none" stroke-dasharray="8" stroke-dashoffset="8" d="M12 12L16 16M12 12L8 8M12 12L8 16M12 12L16 8"%3E%3Canimate fill="freeze" attributeName="stroke-dashoffset" begin="0.6s" dur="0.2s" values="8%3B0"%2F%3E%3C%2Fpath%3E%3C%2Fg%3E%3C%2Fsvg%3E'
            className='resultEmoji'
          ></object>
          <H1 color={colors.gamePink500}>FAILED</H1>
          <object
            type='image/svg+xml'
            data='data:image/svg+xml,%3Csvg xmlns="http%3A%2F%2Fwww.w3.org%2F2000%2Fsvg" width="24" height="24" viewBox="0 0 24 24"%3E%3Cg stroke="%23fe5671" stroke-linecap="round" stroke-width="2"%3E%3Cpath fill="%23fe5671" fill-opacity="0" stroke-dasharray="60" stroke-dashoffset="60" d="M12 3C16.9706 3 21 7.02944 21 12C21 16.9706 16.9706 21 12 21C7.02944 21 3 16.9706 3 12C3 7.02944 7.02944 3 12 3Z"%3E%3Canimate fill="freeze" attributeName="stroke-dashoffset" dur="0.5s" values="60%3B0"%2F%3E%3Canimate fill="freeze" attributeName="fill-opacity" begin="0.8s" dur="0.15s" values="0%3B0.3"%2F%3E%3C%2Fpath%3E%3Cpath fill="none" stroke-dasharray="8" stroke-dashoffset="8" d="M12 12L16 16M12 12L8 8M12 12L8 16M12 12L16 8"%3E%3Canimate fill="freeze" attributeName="stroke-dashoffset" begin="0.6s" dur="0.2s" values="8%3B0"%2F%3E%3C%2Fpath%3E%3C%2Fg%3E%3C%2Fsvg%3E'
            className='resultEmoji'
          ></object>
        </TestModalTitle>
        <TestContainer>
          <TestFailModalContent>
            <div className='flex contentLine'>
              <H4>오답</H4>
              <div className='wrongAnswer'>
                <H4 color={'#FF0000'}>{sentence} (X)</H4>
              </div>
            </div>
            <div className='flex'>
              <H4>정답</H4>
              <div className='rightAnswer'>
                <H4 color={colors.gameBlue300}>{answer.content} (O)</H4>
              </div>
            </div>
          </TestFailModalContent>
        </TestContainer>
        <div className='flex'>
          <CommonBtn
            padding={'1rem 3rem'}
            font={1.5}
            color={colors.gameBlue300}
            hoverColor={colors.gameBlue400}
            fontColor={colors.white}
            border={'none'}
            margin={'0 4rem 0 0'}
            onClick={handleContinue}
          >
            계속
          </CommonBtn>
          <CommonBtn
            padding={'1rem 3rem'}
            font={1.5}
            color={colors.gamePink400}
            hoverColor={colors.gamePink500}
            fontColor={colors.white}
            border={'none'}
            onClick={handleExit}
          >
            종료
          </CommonBtn>
        </div>
      </ModalBasic>
      <TestWrapper>
        <H2>문장 복습 테스트</H2>
        <SentenceTestContentContainer>
          <img src={answer.sentenceImageUrl} className='testImg' alt='testImg' />
          <div className='flex testDesc'>
            <CommonBtn
              width={'8rem'}
              minWidth={'8rem'}
              height={55}
              color={colors.studyBlue200}
              font={1.5}
              fontColor={colors.white}
              border={'none'}
              shadow={'4px 4px 4px rgba(0, 0, 0, 0.25)'}
            >
              해설
            </CommonBtn>
            <div className='translation'>{answer.meaningKr}</div>
          </div>
        </SentenceTestContentContainer>
        <SentenceTestInputContainer>
          <CommonInput
            maxWidth={'720px'}
            border={`3px solid ${colors.gray300}`}
            height={55}
            flexGrow={1}
            font={1.5}
            padding={'1rem'}
            ref={sentenceInput}
            onKeyPress={onEnterSubmit}
            onChange={onChangeInput}
          />
          <CommonBtn
            height={55}
            font={1.5}
            color={colors.gameBlue300}
            hoverColor={colors.gameBlue400}
            fontColor={colors.white}
            border={'none'}
            padding={'12px 36px'}
            margin={'0 0 0 1rem'}
            onClick={handleSubmit}
          >
            SUBMIT
          </CommonBtn>
        </SentenceTestInputContainer>
      </TestWrapper>
    </>
  );
}

export default SentenceTest;
