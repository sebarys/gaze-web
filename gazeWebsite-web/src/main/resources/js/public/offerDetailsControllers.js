/**
 * Created by Mateusz on 2016-04-28.
 */

var offerDetailsControllers = angular.module(
    'OfferDetailsControllers', []);

offerDetailsControllers.controller('OfferDetailsController',
    ['$scope', '$routeParams', 'OfferDetailsService', 'SearchService', '$location', 'toaster', function ($scope, $routeParams, OfferDetailsService, SearchService, $location, toaster) {

        $scope.offerID = $routeParams.offerId;
        $scope.attachments = [];
        $scope.notLogged = {};
        $scope.comments = [];
        $scope.canBuy = [];
        $scope.commentContent = "";
        $scope.rating = 0;


        $scope.offerDetails = OfferDetailsService.getOfferDetails({
            offerId: $scope.offerID
        });

        OfferDetailsService.getAttachements({offerId: $scope.offerID}, function (attachements) {
            $scope.attachments = attachements;
        });

        OfferDetailsService.getComments({offerId: $scope.offerID}, function (comments) {
            $scope.comments = comments;
        });

        $scope.getCommentDate = function (comment) {
            var date = new Date(comment.date);
            return date.getUTCDay() + "." + date.getUTCMonth() + "." + date.getFullYear();
        };

        $scope.redirectToSearchPage = function () {
            var url = '/search';
            if (SearchService.lastQuery !== undefined && SearchService.lastQuery !== null && SearchService.lastQuery !== '') {
                $location.search().query = SearchService.lastQuery;
            }
            if (SearchService.lastPageNumber !== undefined && SearchService.lastPageNumber != null) {
                url = url + '/' + SearchService.lastPageNumber;
            }
            $location.path(url);
        };

        $scope.tooBigQuantity = function () {
            if ($scope.offerDetails.quantity < $scope.order.quantity || $scope.order.quantity === null) {
                $scope.canBuy = false;
                return true;
            } else {
                if (!$scope.notLogged) {
                    $scope.canBuy = true;
                }
                return false;
            }
        };

        OfferDetailsService.getUsername(function (username) {
            if (username.value === null) {
                $scope.notLogged = true;
                $scope.canBuy = false;
                $scope.username = '';
            } else {
                $scope.notLogged = false;
                if (!$scope.tooBigQuantity()) {
                    $scope.canBuy = true;
                }
                $scope.username = username;
            }
        });

        $scope.addComment = function () {
            if ($scope.commentContent.length < 5 || $scope.commentContent.length >= 500) {
                toaster.pop({
                    type: 'warning',
                    title: 'Wrong length of comment',
                    body: 'Your comment must have 5-500 signs',
                    timeout: 3000
                });
            } else {
                $scope.DtoComment = {};
                $scope.DtoComment.offerName = $scope.offerDetails.name;
                $scope.DtoComment.rate = $scope.rating;
                $scope.DtoComment.text = $scope.commentContent;

                OfferDetailsService.addComment($scope.DtoComment);

                var reviewBox = $('#post-review-box');
                var closeReviewBtn = $('#close-review-box');
                reviewBox.slideUp(300);
                closeReviewBtn.hide();

                toaster.pop({
                    type: 'success',
                    title: 'Comment added',
                    timeout: 5000
                });
            }
        };

        $scope.addToBasket = function () {
            if ($scope.notLogged || $scope.tooBigQuantity()) {
                //modal zostaje widoczny
            }
            else {
                $scope.DtoOrder = {};
                $scope.DtoOrder.offerId = $scope.offerID;
                $scope.DtoOrder.offerName = $scope.offerDetails.name;
                $scope.DtoOrder.offerPrice = $scope.offerDetails.price;
                $scope.DtoOrder.offerQuantity = $scope.order.quantity;
                $scope.DtoOrder.amount = $scope.offerDetails.quantity;
                $scope.DtoOrder.basketId = "";
                $scope.DtoOrder.id = "";

                OfferDetailsService.addOrder($scope.DtoOrder, function () {
                });

                toaster.pop({
                    type: 'success',
                    title: 'Added to basket',
                    body: 'Go to basket to finalize order and buy',
                    timeout: 3000
                });
                // $scope.offerDetails = OfferDetailsService.getOfferDetails({
                //     offerId: $scope.offerID
                // });
            }
        };

        // comments pagination
        $scope.currentPage = 0;
        $scope.pageSize = 4;
        $scope.numberOfPages = function () {
            return Math.ceil($scope.comments.length / $scope.pageSize);
        };

        //comment adding
        (function (e) {
            var t, o = {
                className: "autosizejs",
                append: "",
                callback: !1,
                resizeDelay: 10
            }, i = '<textarea tabindex="-1" style="position:absolute; top:-999px; left:0; right:auto; bottom:auto; border:0; padding: 0; -moz-box-sizing:content-box; -webkit-box-sizing:content-box; box-sizing:content-box; word-wrap:break-word; height:0 !important; min-height:0 !important; overflow:hidden; transition:none; -webkit-transition:none; -moz-transition:none;"/>', n = ["fontFamily", "fontSize", "fontWeight", "fontStyle", "letterSpacing", "textTransform", "wordSpacing", "textIndent"], s = e(i).data("autosize", !0)[0];
            s.style.lineHeight = "99px", "99px" === e(s).css("lineHeight") && n.push("lineHeight"), s.style.lineHeight = "", e.fn.autosize = function (i) {
                return this.length ? (i = e.extend({}, o, i || {}), s.parentNode !== document.body && e(document.body).append(s), this.each(function () {
                    function o() {
                        var t, o;
                        "getComputedStyle" in window ? (t = window.getComputedStyle(u, null), o = u.getBoundingClientRect().width, e.each(["paddingLeft", "paddingRight", "borderLeftWidth", "borderRightWidth"], function (e, i) {
                            o -= parseInt(t[i], 10)
                        }), s.style.width = o + "px") : s.style.width = Math.max(p.width(), 0) + "px"
                    }

                    function a() {
                        var a = {};
                        if (t = u, s.className = i.className, d = parseInt(p.css("maxHeight"), 10), e.each(n, function (e, t) {
                                a[t] = p.css(t)
                            }), e(s).css(a), o(), window.chrome) {
                            var r = u.style.width;
                            u.style.width = "0px", u.offsetWidth, u.style.width = r
                        }
                    }

                    function r() {
                        var e, n;
                        t !== u ? a() : o(), s.value = u.value + i.append, s.style.overflowY = u.style.overflowY, n = parseInt(u.style.height, 10), s.scrollTop = 0, s.scrollTop = 9e4, e = s.scrollTop, d && e > d ? (u.style.overflowY = "scroll", e = d) : (u.style.overflowY = "hidden", c > e && (e = c)), e += w, n !== e && (u.style.height = e + "px", f && i.callback.call(u, u))
                    }

                    function l() {
                        clearTimeout(h), h = setTimeout(function () {
                            var e = p.width();
                            e !== g && (g = e, r())
                        }, parseInt(i.resizeDelay, 10))
                    }

                    var d, c, h, u = this, p = e(u), w = 0, f = e.isFunction(i.callback), z = {
                        height: u.style.height,
                        overflow: u.style.overflow,
                        overflowY: u.style.overflowY,
                        wordWrap: u.style.wordWrap,
                        resize: u.style.resize
                    }, g = p.width();
                    p.data("autosize") || (p.data("autosize", !0), ("border-box" === p.css("box-sizing") || "border-box" === p.css("-moz-box-sizing") || "border-box" === p.css("-webkit-box-sizing")) && (w = p.outerHeight() - p.height()), c = Math.max(parseInt(p.css("minHeight"), 10) - w || 0, p.height()), p.css({
                        overflow: "hidden",
                        overflowY: "hidden",
                        wordWrap: "break-word",
                        resize: "none" === p.css("resize") || "vertical" === p.css("resize") ? "none" : "horizontal"
                    }), "onpropertychange" in u ? "oninput" in u ? p.on("input.autosize keyup.autosize", r) : p.on("propertychange.autosize", function () {
                        "value" === event.propertyName && r()
                    }) : p.on("input.autosize", r), i.resizeDelay !== !1 && e(window).on("resize.autosize", l), p.on("autosize.resize", r), p.on("autosize.resizeIncludeStyle", function () {
                        t = null, r()
                    }), p.on("autosize.destroy", function () {
                        t = null, clearTimeout(h), e(window).off("resize", l), p.off("autosize").off(".autosize").css(z).removeData("autosize")
                    }), r())
                })) : this
            }
        })(window.jQuery || window.$);

        var __slice = [].slice;
        (function (e, t) {
            var n;
            n = function () {
                function t(t, n) {
                    var r, i, s, o = this;
                    this.options = e.extend({}, this.defaults, n);
                    this.$el = t;
                    s = this.defaults;
                    for (r in s) {
                        i = s[r];
                        if (this.$el.data(r) != null) {
                            this.options[r] = this.$el.data(r)
                        }
                    }
                    this.createStars();
                    this.syncRating();
                    this.$el.on("mouseover.starrr", "span", function (e) {
                        return o.syncRating(o.$el.find("span").index(e.currentTarget) + 1)
                    });
                    this.$el.on("mouseout.starrr", function () {
                        return o.syncRating()
                    });
                    this.$el.on("click.starrr", "span", function (e) {
                        return o.setRating(o.$el.find("span").index(e.currentTarget) + 1)
                    });
                    this.$el.on("starrr:change", this.options.change)
                }

                t.prototype.defaults = {
                    rating: void 0, numStars: 5, change: function (e, t) {
                    }
                };
                t.prototype.createStars = function () {
                    var e, t, n;
                    n = [];
                    for (e = 1, t = this.options.numStars; 1 <= t ? e <= t : e >= t; 1 <= t ? e++ : e--) {
                        n.push(this.$el.append("<span class='glyphicon .glyphicon-star-empty'></span>"))
                    }
                    return n
                };
                t.prototype.setRating = function (e) {
                    if (this.options.rating === e) {
                        e = void 0
                    }
                    this.options.rating = e;
                    $scope.rating = e;
                    this.syncRating();
                    return this.$el.trigger("starrr:change", e)
                };
                t.prototype.syncRating = function (e) {
                    var t, n, r, i;
                    e || (e = this.options.rating);
                    if (e) {
                        for (t = n = 0, i = e - 1; 0 <= i ? n <= i : n >= i; t = 0 <= i ? ++n : --n) {
                            this.$el.find("span").eq(t).removeClass("glyphicon-star-empty").addClass("glyphicon-star")
                        }
                    }
                    if (e && e < 5) {
                        for (t = r = e; e <= 4 ? r <= 4 : r >= 4; t = e <= 4 ? ++r : --r) {
                            this.$el.find("span").eq(t).removeClass("glyphicon-star").addClass("glyphicon-star-empty")
                        }
                    }
                    if (!e) {
                        return this.$el.find("span").removeClass("glyphicon-star").addClass("glyphicon-star-empty")
                    }
                };
                return t
            }();
            return e.fn.extend({
                starrr: function () {
                    var t, r;
                    r = arguments[0], t = 2 <= arguments.length ? __slice.call(arguments, 1) : [];
                    return this.each(function () {
                        var i;
                        i = e(this).data("star-rating");
                        if (!i) {
                            e(this).data("star-rating", i = new n(e(this), r))
                        }
                        if (typeof r === "string") {
                            return i[r].apply(i, t)
                        }
                    })
                }
            })
        })(window.jQuery, window);
        $(function () {
            return $(".starrr").starrr()
        })

        $(function () {
            $('#new-review').autosize({append: "\n"});
            var reviewBox = $('#post-review-box');
            var newReview = $('#new-review');
            var openReviewBtn = $('#open-review-box');
            var closeReviewBtn = $('#close-review-box');
            var ratingsField = $('#ratings-hidden');

            openReviewBtn.click(function (e) {
                reviewBox.slideDown(400, function () {
                    $('#new-review').trigger('autosize.resize');
                    newReview.focus();
                });
                openReviewBtn.fadeOut(100);
                closeReviewBtn.show();
            });

            closeReviewBtn.click(function (e) {
                e.preventDefault();
                reviewBox.slideUp(300, function () {
                    newReview.focus();
                    openReviewBtn.fadeIn(200);
                });
                closeReviewBtn.hide();
            });
            $('.starrr').on('starrr:change', function (e, value) {
                ratingsField.val(value);
            });
        });

    }]);

offerDetailsControllers.filter('startFrom', function () {
    return function (input, start) {
        start = +start; //parse to int
        return input.slice(start);
    };
});

offerDetailsControllers.filter('range', function () {
    return function (input, total) {
        total = parseInt(total);
        for (var i = 0; i < total; i++) {
            input.push(i);
        }
        return input;
    };
});