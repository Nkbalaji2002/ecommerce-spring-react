import React from "react";
import { FaBeer } from "react-icons/fa";
import Products from "./components/Products";
import { Provider } from "react-redux";
import store from "./store/reducers/store";
import { BrowserRouter } from "react-router-dom";

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
