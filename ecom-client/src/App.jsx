import React from "react";
import { FaBeer } from "react-icons/fa";
import { Provider } from "react-redux";
import store from "./store/reducers/store";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Products from "./components/products/Products";
import Home from "./components/home/Home";
import Navbar from "./components/shared/Navbar";

const App = () => {
  return (
    <>
      <Provider store={store}>
        <Router>
          <Navbar />
          <Routes>
            <Route path="/" Component={Home} />
            <Route path="/products" Component={Products} />
          </Routes>
        </Router>
      </Provider>
    </>
  );
};

export default App;
