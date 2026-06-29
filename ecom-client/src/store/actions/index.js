import api from "../../api/apiURL";

export const fetchProducts = (queryString) => async (dispatch) => {
  try {
    dispatch({
      type: "IS_FETCHING",
    });

    const { data } = await api.get(`/public/products?${queryString}`);

    dispatch({
      type: "FETCH_PRODUCTS",
      payload: data.content,
      pageNumber: data.pageNumber,
      pageSize: data.pageSize,
      totalElements: data.totalElements,
      totalPages: data.totalPages,
      lastPage: data.lastPage,
    });

    dispatch({
      type: "IS_SUCCESS",
    });
  } catch (error) {
    console.log(error);

    dispatch({
      type: "IS_ERROR",
      payload: error?.response?.data?.message || "Failed to fetch Products",
    });
  }
};

export const fetchCategories = (queryString) => async (dispatch) => {
  try {
    dispatch({
      type: "CATEGORY_LOADER",
    });

    const { data } = await api.get(`/public/categories`);

    dispatch({
      type: "FETCH_CATEGORIES",
      payload: data.content,
      pageNumber: data.pageNumber,
      pageSize: data.pageSize,
      totalElements: data.totalElements,
      totalPages: data.totalPages,
      lastPage: data.lastPage,
    });

    dispatch({
      type: "CATEGORY_SUCCESS",
    });
  } catch (error) {
    console.log(error);

    dispatch({
      type: "IS_ERROR",
      payload: error?.response?.data?.message || "Failed to fetch Categories",
    });
  }
};

export const addToCart =
  (data, qty = 1, toast) =>
  (dispatch, getState) => {
    // Find the Product
    const { products } = getState().products;

    const getProduct = products.find(
      (item) => item.productId === data.productId
    );

    // Check for Stocks
    const isQuantityExist = getProduct.quantity >= qty;

    // If in Stocks -> add
    if (isQuantityExist) {
      dispatch({
        type: "ADD_CART",
        payload: {
          ...data,
          quantity: qty,
        },
      });

      toast.success(`${data?.productName} added to the Cart`);
      localStorage.setItem("cartItems", JSON.stringify(getState().carts.cart));
    } else {
      // If not -> error
      toast.error(`Out of Stock`);
    }
  };

export const increaseCartQuantity =
  (data, toast, currentQty, setCurrentQty) => (dispatch, getState) => {
    // Find the Product
    const { products } = getState().products;

    const getProduct = products.find(
      (item) => item.productId === data.productId
    );

    const isQuantityExist = getProduct.quantity >= currentQty + 1;

    if (isQuantityExist) {
      const newQuantity = currentQty + 1;
      setCurrentQty(newQuantity);

      dispatch({
        type: "ADD_CART",
        payload: { ...data, quantity: newQuantity + 1 },
      });

      localStorage.setItem("cartItems", JSON.stringify(getState().carts.cart));
    } else {
      toast.error(`Quantitiy Reached to Limit`);
    }
  };

export const decreaseCartQuantity =
  (data, newQuantity) => (dispatch, getState) => {
    dispatch({
      type: "ADD_CART",
      payload: {
        ...data,
        quantity: newQuantity,
      },
    });

    localStorage.setItem("cartItems", JSON.stringify(getState().carts.cart));
  };

export const removeFromCart = (data, toast) => (dispatch, getState) => {
  dispatch({
    type: "REMOVE_CART",
    payload: data,
  });

  toast.success(`${data.productName} removed from cart`);
  localStorage.setItem("cartItems", JSON.stringify(getState().carts.cart));
};

export const authenticateSignInUser =
  (sendData, toast, reset, navigate, setLoader) => async (dispatch) => {
    try {
      setLoader(true);
      const { data } = await api.post(`/auth/sign-in`, sendData);
      dispatch({
        type: "LOGIN_USER",
        payload: data,
      });

      localStorage.setItem("auth", JSON.stringify(data));
      reset();
      toast.success("Login Success");
      navigate("/");
    } catch (error) {
      toast.error(error?.response?.data?.message || "Internal Server Error");
    } finally {
      setLoader(false);
    }
  };

export const registerNewUser =
  (sendData, toast, reset, navigate, setLoader) => async (dispatch) => {
    try {
      setLoader(true);
      const { data } = await api.post(`/auth/sign-up`, sendData);
      reset();
      toast.success(data?.message || "User Registered Successfully");
      navigate("/login");
    } catch (error) {
      toast.error(
        error?.response?.data?.message ||
          error?.response?.data?.password ||
          "Internal Server Error"
      );
    } finally {
      setLoader(false);
    }
  };

export const logOutUser = (navigate) => (dispatch) => {
  dispatch({ type: "LOG_OUT" });

  localStorage.removeItem("auth");
  navigate("/login");
};
