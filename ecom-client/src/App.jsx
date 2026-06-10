import React from "react";
import { FaBeer } from "react-icons/fa";
import Products from "./components/Products";
import { Provider } from "react-redux";
import store from "./store/reducers/store";

const App = () => {
  return (
    <>
      <Provider store={store}>
        <div>
          <Products />
        </div>
      </Provider>
    </>
  );
};

export default App;
