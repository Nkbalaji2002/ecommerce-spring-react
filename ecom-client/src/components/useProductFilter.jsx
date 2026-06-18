import React, { useEffect } from "react";
import { useDispatch } from "react-redux";
import { useSearchParams } from "react-router-dom";
import { fetchProducts } from "../store/actions";

const useProductFilter = () => {
  const [searchParms] = useSearchParams();
  const dispatch = useDispatch();

  useEffect(() => {
    const params = new URLSearchParams();

    const currentPage = searchParms.get("page")
      ? Number(searchParms.get("page"))
      : 1;

    params.set("pageNumber", currentPage - 1);

    const sortOrder = searchParms.get("sortBy") || "asc";
    const categoryParams = searchParms.get("category") || null;
    const keyword = searchParms.get("keyword") || null;

    params.set("sortBy", "price");
    params.set("sortOrder", sortOrder);

    if (categoryParams) {
      params.set("category", categoryParams);
    }

    if (keyword) {
      params.set("keyword", keyword);
    }

    const queryString = params.toString();
    dispatch(fetchProducts(queryString));
  }, [dispatch, searchParms]);
};

export default useProductFilter;
