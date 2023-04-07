import { Outlet } from "react-router-dom";
import NavBar from "./NavBar";

function NavLayout() {
  return (
    <>
      <NavBar />
      <Outlet />
    </>
  );
}

export default NavLayout;
