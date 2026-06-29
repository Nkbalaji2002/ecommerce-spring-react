import { Avatar, Button, Menu, MenuItem } from "@mui/material";
import React, { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { BiUser } from "react-icons/bi";
import { FaShoppingCart } from "react-icons/fa";
import { IoIosExit } from "react-icons/io";
import { useDispatch, useSelector } from "react-redux";
import BackDrop from "./BackDrop";
import { logOutUser } from "../store/actions";

const UserMenu = () => {
  const id = React.useId();
  const buttonId = `${id}-button`;
  const menuId = `${id}-menu`;

  const [anchorEl, setAnchorEl] = useState(null);
  const open = Boolean(anchorEl);
  const { user } = useSelector((state) => state.auth);

  const dispatch = useDispatch();
  const navigate = useNavigate();

  const handleClick = (event) => {
    setAnchorEl(event.currentTarget);
  };
  const handleClose = () => {
    setAnchorEl(null);
  };

  const logOutHandler = () => {
    dispatch(logOutUser(navigate));
  };

  return (
    <>
      <>
        <div
          className="sm:border sm:border-slate-400 flex flex-row items-center gap-1 rounded-full cursor-pointer hover:shadow-md transition text-slate-700"
          onClick={handleClick}
        >
          <Avatar alt="Menu" />
        </div>
        <Menu
          sx={{
            width: "400px",
          }}
          id={menuId}
          anchorEl={anchorEl}
          open={open}
          onClose={handleClose}
          slotProps={{
            list: {
              "aria-labelledby": buttonId,
              sx: {
                width: 160,
              },
            },
          }}
        >
          <Link to={"/profile"}>
            <MenuItem className="flex gap-2" onClick={handleClose}>
              <BiUser className="text-xl" />
              <span className="font-bold text-[16px] mt-1">
                {user?.username}
              </span>
            </MenuItem>
          </Link>

          <Link to={"/profile/orders"}>
            <MenuItem className="flex gap-2" onClick={handleClose}>
              <FaShoppingCart className="text-xl" />
              <span className="font-semibold">Order</span>
            </MenuItem>
          </Link>

          <MenuItem className="flex gap-2" onClick={logOutHandler}>
            <span className="font-semibold w-full flex gap-2 items-center bg-button-gradient px-4 py-1 text-white rounded-sm">
              <IoIosExit className="text-xl" />
              Logout
            </span>
          </MenuItem>
        </Menu>

        {open && <BackDrop />}
      </>
    </>
  );
};

export default UserMenu;
