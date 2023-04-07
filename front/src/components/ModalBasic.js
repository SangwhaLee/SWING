import React, { useState } from 'react';

import { ModalBackground, ModalBody } from '../styles/CommonEmotion';

function ModalBasic(props) {
  return (
    <>
      {props.modalShow ? (
        <ModalBackground>
          <ModalBody>{props.children}</ModalBody>
        </ModalBackground>
      ) : null}
    </>
  );
}

export default ModalBasic;
