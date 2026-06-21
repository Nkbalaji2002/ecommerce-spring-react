import React from "react";
import { FaBeer } from "react-icons/fa";
import { Provider } from "react-redux";
import store from "./store/reducers/store";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Products from "./components/products/Products";
import Home from "./components/home/Home";
import Navbar from "./components/shared/Navbar";
import About from "./components/About";
import Contact from "./components/Contact";
import { Toaster } from "react-hot-toast";
import Cart from "./components/cart/Cart";

const App = () => {
  return (
    <>
      <Provider store={store}>
        <Router>
          <Navbar />
          <Routes>
            <Route path="/" Component={Home} />
            <Route path="/products" Component={Products} />
            <Route path="/about" Component={About} />
            <Route path="/contact" Component={Contact} />
            <Route path="/cart" Component={Cart} />
          </Routes>
        </Router>
        <>
          <Toaster position="bottom-center" />
        </>
      </Provider>
    </>
  );
};

export default App;
