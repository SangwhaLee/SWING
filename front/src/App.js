import React from 'react';
import { Routes, Route } from 'react-router-dom';
import './App.css';

import Home from './pages/Home';
import LogIn from './pages/LogIn';
import MyPage from './pages/MyPage';
import Sentency from './pages/Sentency';
import Hifive from './pages/Hifive';
import Speedoodle from './pages/Speedoodle';
import SpeedoodleRoom from './pages/SpeedoodleRoom';
import ReviewNote from './pages/ReviewNote';
import History from './pages/History';

import NavLayout from './components/NavLayout';
import LoginLayout from './components/LoginLayout';
import WordTest from './pages/WordTest';
import SentenceTest from './pages/SentenceTest';
import HistoryDetail from './pages/HistoryDetail';

function App() {
  return (
    <>
      <Routes>
        <Route element={<NavLayout />}>
          <Route path='/' element={<Home />} />
          <Route path='/my-page' element={<MyPage />} />
          <Route path='/sentency' element={<Sentency />} />
          <Route path='/hi-five' element={<Hifive />} />
          <Route path='/speedoodle' element={<Speedoodle />} />
          <Route path='/speedoodle/room/:no' element={<SpeedoodleRoom />} />
          <Route path='/review-note' element={<ReviewNote />} />
          <Route path='/history' element={<History />} />
          <Route path='/history/:no' element={<HistoryDetail />} />
          <Route path='/test-word' element={<WordTest />} />
          <Route path='/test-sentence' element={<SentenceTest />} />
        </Route>
        <Route element={<LoginLayout />}>
          <Route path='/login' element={<LogIn />} />
        </Route>
      </Routes>
    </>
  );
}

export default App;
