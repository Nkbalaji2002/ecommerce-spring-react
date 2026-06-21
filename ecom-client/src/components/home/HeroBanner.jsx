import React from "react";
import { Swiper, SwiperSlide } from "swiper/react";
import {
  A11y,
  Autoplay,
  EffectFade,
  Navigation,
  Pagination,
  Scrollbar,
} from "swiper/modules";
import "swiper/css";
import { bannerLists } from "../../utils";
import { Link } from "react-router-dom";

const colors = [
  "bg-banner-color1",
  "bg-banner-color2",
  "bg-banner-color3",
  "bg-banner-color4",
];

const HeroBanner = () => {
  return (
    <>
      <div className="py-2 rounded-md">
        <Swiper
          grabCursor={true}
          autoplay={{
            delay: 4000,
            disableOnInteraction: false,
          }}
          navigation
          modules={[Pagination, EffectFade, Navigation, Autoplay]}
          pagination={{ clickable: true }}
          scrollbar={{
            draggable: true,
          }}
          slidesPerView={1}
        >
          {bannerLists.map((item, i) => (
            <>
              <SwiperSlide key={item.id}>
                <div
                  className={`carousel-item rounded-md sm:h-125 h-96 ${colors[i]}`}
                >
                  <div className="flex flex-col lg:flex-row items-center justify-between h-full max-w-7xl mx-auto px-8">
                    {/* Content */}
                    <div className="hidden lg:flex flex-col justify-center w-1/2">
                      <h3 className="text-3xl text-white font-bold">
                        {item.title}
                      </h3>

                      <h1 className="text-6xl text-white font-bold mt-2">
                        {item.subtitle}
                      </h1>

                      <p className="text-white mt-4 text-lg">
                        {item.description}
                      </p>

                      <Link
                        to="/products"
                        className="mt-8 w-fit bg-black text-white px-6 py-3 rounded-lg hover:scale-105 transition"
                      >
                        Shop Now
                      </Link>
                    </div>

                    {/* Image */}
                    <div className="flex justify-center w-full lg:w-1/2">
                      <img
                        src={item.image}
                        alt=""
                        className="max-w-full lg:max-w-137.5 object-contain drop-shadow-2xl"
                      />
                    </div>
                  </div>
                </div>
              </SwiperSlide>
            </>
          ))}
        </Swiper>
      </div>
    </>
  );
};

export default HeroBanner;
