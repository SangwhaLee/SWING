import React, {useState} from 'react';
import { PaginationBox, PageNumBox } from '../styles/ReviewNoteEmotion';
import { P1 } from '../styles/Fonts';
import { ChevronLeft, ChevronRight, ChevronDoubleLeft, ChevronDoubleRight } from 'react-bootstrap-icons';

function Pagination({total,limit, page, setPage, Ppage, setPpage}) {
    const numPages = Math.ceil(total / limit);
    const numPpages = Math.ceil(numPages / 5);

    const renderPpageNum  = () => {
      if(Ppage < numPpages){
        return Array(5).fill();
      }
      else{
        return Array((numPages - (Ppage-1)*5)).fill();
      }
    };

    const leftClicked = () => {
      if(page - 1 === 0){
        setPpage(Ppage -1);
        setPage(5);
      }
      else{
        setPage(page - 1);
      }
    };

    const rightClicked = () => {
      if((page + 1)%5 === 1){
        setPpage(Ppage + 1);
        setPage(1);
      }
      else{
        setPage(page + 1);
      }
    };

    const doubleleftClicked = () => {
      setPpage(Ppage - 1);
      setPage(5);
    };

    const doublerightClicked = () => {
      setPpage(Ppage + 1);
      setPage(1);
    };

    return(
        <>
          <PaginationBox>
            <PageNumBox
            onClick={doubleleftClicked}
            disabled={Ppage===1 || total === 0}>
              <ChevronDoubleLeft />
            </PageNumBox>
            <PageNumBox onClick={leftClicked} 
              disabled={(page === 1 && Ppage === 1) || total === 0}>
              <ChevronLeft/>
            </PageNumBox>
            {renderPpageNum().map((_, i) => (
            <PageNumBox
              key={i + 1}
              onClick={() => setPage(i + 1)}
              aria-current={page === i + 1 ? "page" : null}
            >
              <P1>{i + 1 + (Ppage-1)*5}</P1>
            </PageNumBox>
          ))}
          <PageNumBox onClick={rightClicked} 
              disabled={(page === renderPpageNum().length && Ppage === numPpages) || total === 0}>
              <ChevronRight/>
          </PageNumBox>
          <PageNumBox
          onClick={doublerightClicked}
          disabled={Ppage===numPpages || total === 0}>
              <ChevronDoubleRight/>
          </PageNumBox>
          </PaginationBox>
        </>
    )
}

export default Pagination;