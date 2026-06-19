import React from "react";
import { FaBeer } from "react-icons/fa";
import { Provider } from "react-redux";
import store from "./store/reducers/store";
import { BrowserRouter } from "react-router-dom";
import Products from "./components/products/Products";

const App = () => {
  return (
    <>
      <Provider store={store}>
        <BrowserRouter>
          <>
            <Products />
          </>
        </BrowserRouter>
      </Provider>
    </>
  );
};

export default App;
