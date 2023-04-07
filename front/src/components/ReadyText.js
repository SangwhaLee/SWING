import React, { useState, useEffect } from 'react';
import useInterval from '../hooks/useInterval';

import { StartAlert } from '../styles/SpeedoodleGameEmotion';

function ReadyText(props) {
  const [readyText, setReadyText] = useState('');
  const [readyIdx, setReadyIdx] = useState(0);
  const ready = ['3', '2', '1', 'START'];

  useInterval(() => {
    if (!props.readyGame) {
      if (readyIdx < 4) {
        setReadyText(ready[readyIdx]);
        setReadyIdx((prev) => prev + 1);
      } else {
        clearInterval();
        props.setReadyGame(true);
        setReadyIdx(0);
        setReadyText('');
      }
    }
  }, 1200);

  return props.readyGame ? '' : <StartAlert>{readyText}</StartAlert>;
}

export default ReadyText;
