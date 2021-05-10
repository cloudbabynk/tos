! function (t, e) {
    "object" == typeof exports && "object" == typeof module ? module.exports = e(require("jQuery")) : "function" == typeof define && define.amd ? define("hdship", ["jQuery"], e) : "object" == typeof exports ? exports.hdship = e(require("jQuery")) : t.hdship = e(t.jQuery)
}(window, function (t) {
    return function (t) {
        var e = {};

        function r(n) {
            if (e[n]) return e[n].exports;
            var i = e[n] = {
                i: n,
                l: !1,
                exports: {}
            };
            return t[n].call(i.exports, i, i.exports, r), i.l = !0, i.exports
        }
        return r.m = t, r.c = e, r.d = function (t, e, n) {
            r.o(t, e) || Object.defineProperty(t, e, {
                configurable: !1,
                enumerable: !0,
                get: n
            })
        }, r.r = function (t) {
            Object.defineProperty(t, "__esModule", {
                value: !0
            })
        }, r.n = function (t) {
            var e = t && t.__esModule ? function () {
                return t.default
            } : function () {
                return t
            };
            return r.d(e, "a", e), e
        }, r.o = function (t, e) {
            return Object.prototype.hasOwnProperty.call(t, e)
        }, r.p = "", r(r.s = 103)
    }([
        function (t, e) {
            var r = {
                    "[object Function]": 1,
                    "[object RegExp]": 1,
                    "[object Date]": 1,
                    "[object Error]": 1,
                    "[object CanvasGradient]": 1,
                    "[object CanvasPattern]": 1,
                    "[object Image]": 1,
                    "[object Canvas]": 1
                },
                n = {
                    "[object Int8Array]": 1,
                    "[object Uint8Array]": 1,
                    "[object Uint8ClampedArray]": 1,
                    "[object Int16Array]": 1,
                    "[object Uint16Array]": 1,
                    "[object Int32Array]": 1,
                    "[object Uint32Array]": 1,
                    "[object Float32Array]": 1,
                    "[object Float64Array]": 1
                },
                i = Object.prototype.toString,
                o = Array.prototype,
                a = o.forEach,
                s = o.filter,
                u = o.slice,
                l = o.map,
                h = o.reduce,
                c = {};

            function f(t) {
                if (null == t || "object" != typeof t) return t;
                var e = t,
                    o = i.call(t);
                if ("[object Array]" === o) {
                    if (!k(t)) {
                        e = [];
                        for (var a = 0, s = t.length; a < s; a++) e[a] = f(t[a])
                    }
                } else if (n[o]) {
                    if (!k(t)) {
                        var u = t.constructor;
                        if (t.constructor.from) e = u.from(t);
                        else {
                            e = new u(t.length);
                            for (a = 0, s = t.length; a < s; a++) e[a] = f(t[a])
                        }
                    }
                } else if (!r[o] && !k(t) && !w(t))
                    for (var l in e = {}, t) t.hasOwnProperty(l) && (e[l] = f(t[l]));
                return e
            }

            function d(t, e, r) {
                if (!x(e) || !x(t)) return r ? f(e) : t;
                for (var n in e)
                    if (e.hasOwnProperty(n)) {
                        var i = t[n],
                            o = e[n];
                        !x(o) || !x(i) || m(o) || m(i) || w(o) || w(i) || b(o) || b(i) || k(o) || k(i) ? !r && n in t || (t[n] = f(e[n])) : d(i, o, r)
                    }
                return t
            }

            function p(t, e, r) {
                for (var n in e) e.hasOwnProperty(n) && (r ? null != e[n] : null == t[n]) && (t[n] = e[n]);
                return t
            }
            var v, g = function () {
                return c.createCanvas()
            };

            function _(t, e, r) {
                if (t && e)
                    if (t.forEach && t.forEach === a) t.forEach(e, r);
                    else if (t.length === +t.length)
                    for (var n = 0, i = t.length; n < i; n++) e.call(r, t[n], n, t);
                else
                    for (var o in t) t.hasOwnProperty(o) && e.call(r, t[o], o, t)
            }

            function y(t, e) {
                var r = u.call(arguments, 2);
                return function () {
                    return t.apply(e, r.concat(u.call(arguments)))
                }
            }

            function m(t) {
                return "[object Array]" === i.call(t)
            }

            function x(t) {
                var e = typeof t;
                return "function" === e || !!t && "object" == e
            }

            function b(t) {
                return !!r[i.call(t)]
            }

            function w(t) {
                return "object" == typeof t && "number" == typeof t.nodeType && "object" == typeof t.ownerDocument
            }
            c.createCanvas = function () {
                return document.createElement("canvas")
            };
            var S = "__ec_primitive__";

            function k(t) {
                return t[S]
            }

            function T(t) {
                var e = m(t),
                    r = this;

                function n(t, n) {
                    e ? r.set(t, n) : r.set(n, t)
                }
                t instanceof T ? t.each(n) : t && _(t, n)
            }
            T.prototype = {
                constructor: T,
                get: function (t) {
                    return this.hasOwnProperty(t) ? this[t] : null
                }, set: function (t, e) {
                    return this[t] = e
                }, each: function (t, e) {
                    for (var r in void 0 !== e && (t = y(t, e)), this) this.hasOwnProperty(r) && t(this[r], r)
                }, removeKey: function (t) {
                    delete this[t]
                }
            }, e.$override = function (t, e) {
                "createCanvas" === t && (v = null), c[t] = e
            }, e.clone = f, e.merge = d, e.mergeAll = function (t, e) {
                for (var r = t[0], n = 1, i = t.length; n < i; n++) r = d(r, t[n], e);
                return r
            }, e.extend = function (t, e) {
                for (var r in e) e.hasOwnProperty(r) && (t[r] = e[r]);
                return t
            }, e.defaults = p, e.createCanvas = g, e.getContext = function () {
                return v || (v = g().getContext("2d")), v
            }, e.indexOf = function (t, e) {
                if (t) {
                    if (t.indexOf) return t.indexOf(e);
                    for (var r = 0, n = t.length; r < n; r++)
                        if (t[r] === e) return r
                }
                return -1
            }, e.inherits = function (t, e) {
                var r = t.prototype;

                function n() {}
                for (var i in n.prototype = e.prototype, t.prototype = new n, r) t.prototype[i] = r[i];
                t.prototype.constructor = t, t.superClass = e
            }, e.mixin = function (t, e, r) {
                p(t = "prototype" in t ? t.prototype : t, e = "prototype" in e ? e.prototype : e, r)
            }, e.isArrayLike = function (t) {
                if (t) return "string" != typeof t && "number" == typeof t.length
            }, e.each = _, e.map = function (t, e, r) {
                if (t && e) {
                    if (t.map && t.map === l) return t.map(e, r);
                    for (var n = [], i = 0, o = t.length; i < o; i++) n.push(e.call(r, t[i], i, t));
                    return n
                }
            }, e.reduce = function (t, e, r, n) {
                if (t && e) {
                    if (t.reduce && t.reduce === h) return t.reduce(e, r, n);
                    for (var i = 0, o = t.length; i < o; i++) r = e.call(n, r, t[i], i, t);
                    return r
                }
            }, e.filter = function (t, e, r) {
                if (t && e) {
                    if (t.filter && t.filter === s) return t.filter(e, r);
                    for (var n = [], i = 0, o = t.length; i < o; i++) e.call(r, t[i], i, t) && n.push(t[i]);
                    return n
                }
            }, e.find = function (t, e, r) {
                if (t && e)
                    for (var n = 0, i = t.length; n < i; n++)
                        if (e.call(r, t[n], n, t)) return t[n]
            }, e.bind = y, e.curry = function (t) {
                var e = u.call(arguments, 1);
                return function () {
                    return t.apply(this, e.concat(u.call(arguments)))
                }
            }, e.isArray = m, e.isFunction = function (t) {
                return "function" == typeof t
            }, e.isString = function (t) {
                return "[object String]" === i.call(t)
            }, e.isObject = x, e.isBuiltInObject = b, e.isTypedArray = function (t) {
                return !!n[i.call(t)]
            }, e.isDom = w, e.eqNaN = function (t) {
                return t != t
            }, e.retrieve = function (t) {
                for (var e = 0, r = arguments.length; e < r; e++)
                    if (null != arguments[e]) return arguments[e]
            }, e.retrieve2 = function (t, e) {
                return null != t ? t : e
            }, e.retrieve3 = function (t, e, r) {
                return null != t ? t : null != e ? e : r
            }, e.slice = function () {
                return Function.call.apply(u, arguments)
            }, e.normalizeCssArray = function (t) {
                if ("number" == typeof t) return [t, t, t, t];
                var e = t.length;
                return 2 === e ? [t[0], t[1], t[0], t[1]] : 3 === e ? [t[0], t[1], t[2], t[1]] : t
            }, e.assert = function (t, e) {
                if (!t) throw new Error(e)
            }, e.trim = function (t) {
                return null == t ? null : "function" == typeof t.trim ? t.trim() : t.replace(/^[\s\uFEFF\xA0]+|[\s\uFEFF\xA0]+$/g, "")
            }, e.setAsPrimitive = function (t) {
                t[S] = !0
            }, e.isPrimitive = k, e.createHashMap = function (t) {
                return new T(t)
            }, e.concatArray = function (t, e) {
                for (var r = new t.constructor(t.length + e.length), n = 0; n < t.length; n++) r[n] = t[n];
                var i = t.length;
                for (n = 0; n < e.length; n++) r[n + i] = e[n];
                return r
            }, e.noop = function () {}
        },
        function (t, e, r) {
            var n = r(8),
                i = r(0),
                o = r(6),
                a = r(83),
                s = r(22).prototype.getCanvasPattern,
                u = Math.abs,
                l = new o(!0);

            function h(t) {
                n.call(this, t), this.path = null
            }
            h.prototype = {
                constructor: h,
                type: "path",
                __dirtyPath: !0,
                strokeContainThreshold: 5,
                brush: function (t, e) {
                    var r, n = this.style,
                        i = this.path || l,
                        o = n.hasStroke(),
                        a = n.hasFill(),
                        u = n.fill,
                        h = n.stroke,
                        c = a && !!u.colorStops,
                        f = o && !!h.colorStops,
                        d = a && !!u.image,
                        p = o && !!h.image;
                    (n.bind(t, this, e), this.setTransform(t), this.__dirty) && (c && (r = r || this.getBoundingRect(), this._fillGradient = n.getGradient(t, u, r)), f && (r = r || this.getBoundingRect(), this._strokeGradient = n.getGradient(t, h, r)));
                    c ? t.fillStyle = this._fillGradient : d && (t.fillStyle = s.call(u, t)), f ? t.strokeStyle = this._strokeGradient : p && (t.strokeStyle = s.call(h, t));
                    var v = n.lineDash,
                        g = n.lineDashOffset,
                        _ = !!t.setLineDash,
                        y = this.getGlobalScale();
                    i.setScale(y[0], y[1]), this.__dirtyPath || v && !_ && o ? (i.beginPath(t), v && !_ && (i.setLineDash(v), i.setLineDashOffset(g)), this.buildPath(i, this.shape, !1), this.path && (this.__dirtyPath = !1)) : (t.beginPath(), this.path.rebuildPath(t)), a && i.fill(t), v && _ && (t.setLineDash(v), t.lineDashOffset = g), o && i.stroke(t), v && _ && t.setLineDash([]), null != n.text && (this.restoreTransform(t), this.drawRectText(t, this.getBoundingRect()))
                }, buildPath: function (t, e, r) {}, createPathProxy: function () {
                    this.path = new o
                }, getBoundingRect: function () {
                    var t = this._rect,
                        e = this.style,
                        r = !t;
                    if (r) {
                        var n = this.path;
                        n || (n = this.path = new o), this.__dirtyPath && (n.beginPath(), this.buildPath(n, this.shape, !1)), t = n.getBoundingRect()
                    }
                    if (this._rect = t, e.hasStroke()) {
                        var i = this._rectWithStroke || (this._rectWithStroke = t.clone());
                        if (this.__dirty || r) {
                            i.copy(t);
                            var a = e.lineWidth,
                                s = e.strokeNoScale ? this.getLineScale() : 1;
                            e.hasFill() || (a = Math.max(a, this.strokeContainThreshold || 4)), s > 1e-10 && (i.width += a / s, i.height += a / s, i.x -= a / s / 2, i.y -= a / s / 2)
                        }
                        return i
                    }
                    return t
                }, contain: function (t, e) {
                    var r = this.transformCoordToLocal(t, e),
                        n = this.getBoundingRect(),
                        i = this.style;
                    if (t = r[0], e = r[1], n.contain(t, e)) {
                        var o = this.path.data;
                        if (i.hasStroke()) {
                            var s = i.lineWidth,
                                u = i.strokeNoScale ? this.getLineScale() : 1;
                            if (u > 1e-10 && (i.hasFill() || (s = Math.max(s, this.strokeContainThreshold)), a.containStroke(o, s / u, t, e))) return !0
                        }
                        if (i.hasFill()) return a.contain(o, t, e)
                    }
                    return !1
                }, dirty: function (t) {
                    null == t && (t = !0), t && (this.__dirtyPath = t, this._rect = null), this.__dirty = !0, this.__zr && this.__zr.refresh(), this.__clipTarget && this.__clipTarget.dirty()
                }, animateShape: function (t) {
                    return this.animate("shape", t)
                }, attrKV: function (t, e) {
                    "shape" === t ? (this.setShape(e), this.__dirtyPath = !0, this._rect = null) : n.prototype.attrKV.call(this, t, e)
                }, setShape: function (t, e) {
                    var r = this.shape;
                    if (r) {
                        if (i.isObject(t))
                            for (var n in t) t.hasOwnProperty(n) && (r[n] = t[n]);
                        else r[t] = e;
                        this.dirty(!0)
                    }
                    return this
                }, getLineScale: function () {
                    var t = this.transform;
                    return t && u(t[0] - 1) > 1e-10 && u(t[3] - 1) > 1e-10 ? Math.sqrt(u(t[0] * t[3] - t[2] * t[1])) : 1
                }
            }, h.extend = function (t) {
                var e = function (e) {
                    h.call(this, e), t.style && this.style.extendFrom(t.style, !1);
                    var r = t.shape;
                    if (r) {
                        this.shape = this.shape || {};
                        var n = this.shape;
                        for (var i in r)!n.hasOwnProperty(i) && r.hasOwnProperty(i) && (n[i] = r[i])
                    }
                    t.init && t.init.call(this, e)
                };
                for (var r in i.inherits(e, h), t) "style" !== r && "shape" !== r && (e.prototype[r] = t[r]);
                return e
            }, i.inherits(h, n);
            var c = h;
            t.exports = c
        },
        function (t, e) {
            var r = "undefined" == typeof Float32Array ? Array : Float32Array;

            function n(t) {
                return Math.sqrt(o(t))
            }
            var i = n;

            function o(t) {
                return t[0] * t[0] + t[1] * t[1]
            }
            var a = o;

            function s(t, e) {
                return Math.sqrt((t[0] - e[0]) * (t[0] - e[0]) + (t[1] - e[1]) * (t[1] - e[1]))
            }
            var u = s;

            function l(t, e) {
                return (t[0] - e[0]) * (t[0] - e[0]) + (t[1] - e[1]) * (t[1] - e[1])
            }
            var h = l;
            e.create = function (t, e) {
                var n = new r(2);
                return null == t && (t = 0), null == e && (e = 0), n[0] = t, n[1] = e, n
            }, e.copy = function (t, e) {
                return t[0] = e[0], t[1] = e[1], t
            }, e.clone = function (t) {
                var e = new r(2);
                return e[0] = t[0], e[1] = t[1], e
            }, e.set = function (t, e, r) {
                return t[0] = e, t[1] = r, t
            }, e.add = function (t, e, r) {
                return t[0] = e[0] + r[0], t[1] = e[1] + r[1], t
            }, e.scaleAndAdd = function (t, e, r, n) {
                return t[0] = e[0] + r[0] * n, t[1] = e[1] + r[1] * n, t
            }, e.sub = function (t, e, r) {
                return t[0] = e[0] - r[0], t[1] = e[1] - r[1], t
            }, e.len = n, e.length = i, e.lenSquare = o, e.lengthSquare = a, e.mul = function (t, e, r) {
                return t[0] = e[0] * r[0], t[1] = e[1] * r[1], t
            }, e.div = function (t, e, r) {
                return t[0] = e[0] / r[0], t[1] = e[1] / r[1], t
            }, e.dot = function (t, e) {
                return t[0] * e[0] + t[1] * e[1]
            }, e.scale = function (t, e, r) {
                return t[0] = e[0] * r, t[1] = e[1] * r, t
            }, e.normalize = function (t, e) {
                var r = n(e);
                return 0 === r ? (t[0] = 0, t[1] = 0) : (t[0] = e[0] / r, t[1] = e[1] / r), t
            }, e.distance = s, e.dist = u, e.distanceSquare = l, e.distSquare = h, e.negate = function (t, e) {
                return t[0] = -e[0], t[1] = -e[1], t
            }, e.lerp = function (t, e, r, n) {
                return t[0] = e[0] + n * (r[0] - e[0]), t[1] = e[1] + n * (r[1] - e[1]), t
            }, e.applyTransform = function (t, e, r) {
                var n = e[0],
                    i = e[1];
                return t[0] = r[0] * n + r[2] * i + r[4], t[1] = r[1] * n + r[3] * i + r[5], t
            }, e.min = function (t, e, r) {
                return t[0] = Math.min(e[0], r[0]), t[1] = Math.min(e[1], r[1]), t
            }, e.max = function (t, e, r) {
                return t[0] = Math.max(e[0], r[0]), t[1] = Math.max(e[1], r[1]), t
            }
        },
        function (t, e, r) {
            var n, i, o, a, s = r(2),
                u = r(14),
                l = s.applyTransform,
                h = Math.min,
                c = Math.max;

            function f(t, e, r, n) {
                r < 0 && (t += r, r = -r), n < 0 && (e += n, n = -n), this.x = t, this.y = e, this.width = r, this.height = n
            }
            f.prototype = {
                constructor: f,
                union: function (t) {
                    var e = h(t.x, this.x),
                        r = h(t.y, this.y);
                    this.width = c(t.x + t.width, this.x + this.width) - e, this.height = c(t.y + t.height, this.y + this.height) - r, this.x = e, this.y = r
                }, applyTransform: (n = [], i = [], o = [], a = [], function (t) {
                    if (t) {
                        n[0] = o[0] = this.x, n[1] = a[1] = this.y, i[0] = a[0] = this.x + this.width, i[1] = o[1] = this.y + this.height, l(n, n, t), l(i, i, t), l(o, o, t), l(a, a, t), this.x = h(n[0], i[0], o[0], a[0]), this.y = h(n[1], i[1], o[1], a[1]);
                        var e = c(n[0], i[0], o[0], a[0]),
                            r = c(n[1], i[1], o[1], a[1]);
                        this.width = e - this.x, this.height = r - this.y
                    }
                }),
                calculateTransform: function (t) {
                    var e = this,
                        r = t.width / e.width,
                        n = t.height / e.height,
                        i = u.create();
                    return u.translate(i, i, [-e.x, -e.y]), u.scale(i, i, [r, n]), u.translate(i, i, [t.x, t.y]), i
                }, intersect: function (t) {
                    if (!t) return !1;
                    t instanceof f || (t = f.create(t));
                    var e = this,
                        r = e.x,
                        n = e.x + e.width,
                        i = e.y,
                        o = e.y + e.height,
                        a = t.x,
                        s = t.x + t.width,
                        u = t.y,
                        l = t.y + t.height;
                    return !(n < a || s < r || o < u || l < i)
                }, contain: function (t, e) {
                    return t >= this.x && t <= this.x + this.width && e >= this.y && e <= this.y + this.height
                }, clone: function () {
                    return new f(this.x, this.y, this.width, this.height)
                }, copy: function (t) {
                    this.x = t.x, this.y = t.y, this.width = t.width, this.height = t.height
                }, plain: function () {
                    return {
                        x: this.x,
                        y: this.y,
                        width: this.width,
                        height: this.height
                    }
                }
            }, f.create = function (t) {
                return new f(t.x, t.y, t.width, t.height)
            };
            var d = f;
            t.exports = d
        },
        function (t, e) {
            var r = "undefined" != typeof wx ? {
                browser: {},
                os: {},
                node: !1,
                wxa: !0,
                canvasSupported: !0,
                svgSupported: !1,
                touchEventsSupported: !0
            } : "undefined" == typeof document && "undefined" != typeof self ? {
                browser: {},
                os: {},
                node: !1,
                worker: !0,
                canvasSupported: !0
            } : "undefined" == typeof navigator ? {
                browser: {},
                os: {},
                node: !0,
                worker: !1,
                canvasSupported: !0,
                svgSupported: !0
            } : function (t) {
                var e = {},
                    r = t.match(/Firefox\/([\d.]+)/),
                    n = t.match(/MSIE\s([\d.]+)/) || t.match(/Trident\/.+?rv:(([\d.]+))/),
                    i = t.match(/Edge\/([\d.]+)/),
                    o = /micromessenger/i.test(t);
                r && (e.firefox = !0, e.version = r[1]);
                n && (e.ie = !0, e.version = n[1]);
                i && (e.edge = !0, e.version = i[1]);
                o && (e.weChat = !0);
                return {
                    browser: e,
                    os: {},
                    node: !1,
                    canvasSupported: !!document.createElement("canvas").getContext,
                    svgSupported: "undefined" != typeof SVGRect,
                    touchEventsSupported: "ontouchstart" in window && !e.ie && !e.edge,
                    pointerEventsSupported: "onpointerdown" in window && (e.edge || e.ie && e.version >= 11)
                }
            }(navigator.userAgent);
            t.exports = r
        },
        function (t, e, r) {
            var n = r(2),
                i = n.create,
                o = n.distSquare,
                a = Math.pow,
                s = Math.sqrt,
                u = 1e-8,
                l = 1e-4,
                h = s(3),
                c = 1 / 3,
                f = i(),
                d = i(),
                p = i();

            function v(t) {
                return t > -u && t < u
            }

            function g(t) {
                return t > u || t < -u
            }

            function _(t, e, r, n, i) {
                var o = 1 - i;
                return o * o * (o * t + 3 * i * e) + i * i * (i * n + 3 * o * r)
            }

            function y(t, e, r, n) {
                var i = 1 - n;
                return i * (i * t + 2 * n * e) + n * n * r
            }
            e.cubicAt = _, e.cubicDerivativeAt = function (t, e, r, n, i) {
                var o = 1 - i;
                return 3 * (((e - t) * o + 2 * (r - e) * i) * o + (n - r) * i * i)
            }, e.cubicRootAt = function (t, e, r, n, i, o) {
                var u = n + 3 * (e - r) - t,
                    l = 3 * (r - 2 * e + t),
                    f = 3 * (e - t),
                    d = t - i,
                    p = l * l - 3 * u * f,
                    g = l * f - 9 * u * d,
                    _ = f * f - 3 * l * d,
                    y = 0;
                if (v(p) && v(g)) v(l) ? o[0] = 0 : (P = -f / l) >= 0 && P <= 1 && (o[y++] = P);
                else {
                    var m = g * g - 4 * p * _;
                    if (v(m)) {
                        var x = g / p,
                            b = -x / 2;
                        (P = -l / u + x) >= 0 && P <= 1 && (o[y++] = P), b >= 0 && b <= 1 && (o[y++] = b)
                    } else if (m > 0) {
                        var w = s(m),
                            S = p * l + 1.5 * u * (-g + w),
                            k = p * l + 1.5 * u * (-g - w);
                        (P = (-l - ((S = S < 0 ? -a(-S, c) : a(S, c)) + (k = k < 0 ? -a(-k, c) : a(k, c)))) / (3 * u)) >= 0 && P <= 1 && (o[y++] = P)
                    } else {
                        var T = (2 * p * l - 3 * u * g) / (2 * s(p * p * p)),
                            C = Math.acos(T) / 3,
                            A = s(p),
                            L = Math.cos(C),
                            P = (-l - 2 * A * L) / (3 * u),
                            M = (b = (-l + A * (L + h * Math.sin(C))) / (3 * u), (-l + A * (L - h * Math.sin(C))) / (3 * u));
                        P >= 0 && P <= 1 && (o[y++] = P), b >= 0 && b <= 1 && (o[y++] = b), M >= 0 && M <= 1 && (o[y++] = M)
                    }
                }
                return y
            }, e.cubicExtrema = function (t, e, r, n, i) {
                var o = 6 * r - 12 * e + 6 * t,
                    a = 9 * e + 3 * n - 3 * t - 9 * r,
                    u = 3 * e - 3 * t,
                    l = 0;
                if (v(a)) g(o) && (c = -u / o) >= 0 && c <= 1 && (i[l++] = c);
                else {
                    var h = o * o - 4 * a * u;
                    if (v(h)) i[0] = -o / (2 * a);
                    else if (h > 0) {
                        var c, f = s(h),
                            d = (-o - f) / (2 * a);
                        (c = (-o + f) / (2 * a)) >= 0 && c <= 1 && (i[l++] = c), d >= 0 && d <= 1 && (i[l++] = d)
                    }
                }
                return l
            }, e.cubicSubdivide = function (t, e, r, n, i, o) {
                var a = (e - t) * i + t,
                    s = (r - e) * i + e,
                    u = (n - r) * i + r,
                    l = (s - a) * i + a,
                    h = (u - s) * i + s,
                    c = (h - l) * i + l;
                o[0] = t, o[1] = a, o[2] = l, o[3] = c, o[4] = c, o[5] = h, o[6] = u, o[7] = n
            }, e.cubicProjectPoint = function (t, e, r, n, i, a, u, h, c, v, g) {
                var y, m, x, b, w, S = .005,
                    k = 1 / 0;
                f[0] = c, f[1] = v;
                for (var T = 0; T < 1; T += .05) d[0] = _(t, r, i, u, T), d[1] = _(e, n, a, h, T), (b = o(f, d)) < k && (y = T, k = b);
                k = 1 / 0;
                for (var C = 0; C < 32 && !(S < l); C++) m = y - S, x = y + S, d[0] = _(t, r, i, u, m), d[1] = _(e, n, a, h, m), b = o(d, f), m >= 0 && b < k ? (y = m, k = b) : (p[0] = _(t, r, i, u, x), p[1] = _(e, n, a, h, x), w = o(p, f), x <= 1 && w < k ? (y = x, k = w) : S *= .5);
                return g && (g[0] = _(t, r, i, u, y), g[1] = _(e, n, a, h, y)), s(k)
            }, e.quadraticAt = y, e.quadraticDerivativeAt = function (t, e, r, n) {
                return 2 * ((1 - n) * (e - t) + n * (r - e))
            }, e.quadraticRootAt = function (t, e, r, n, i) {
                var o = t - 2 * e + r,
                    a = 2 * (e - t),
                    u = t - n,
                    l = 0;
                if (v(o)) g(a) && (c = -u / a) >= 0 && c <= 1 && (i[l++] = c);
                else {
                    var h = a * a - 4 * o * u;
                    if (v(h))(c = -a / (2 * o)) >= 0 && c <= 1 && (i[l++] = c);
                    else if (h > 0) {
                        var c, f = s(h),
                            d = (-a - f) / (2 * o);
                        (c = (-a + f) / (2 * o)) >= 0 && c <= 1 && (i[l++] = c), d >= 0 && d <= 1 && (i[l++] = d)
                    }
                }
                return l
            }, e.quadraticExtremum = function (t, e, r) {
                var n = t + r - 2 * e;
                return 0 === n ? .5 : (t - e) / n
            }, e.quadraticSubdivide = function (t, e, r, n, i) {
                var o = (e - t) * n + t,
                    a = (r - e) * n + e,
                    s = (a - o) * n + o;
                i[0] = t, i[1] = o, i[2] = s, i[3] = s, i[4] = a, i[5] = r
            }, e.quadraticProjectPoint = function (t, e, r, n, i, a, u, h, c) {
                var v, g = .005,
                    _ = 1 / 0;
                f[0] = u, f[1] = h;
                for (var m = 0; m < 1; m += .05) d[0] = y(t, r, i, m), d[1] = y(e, n, a, m), (S = o(f, d)) < _ && (v = m, _ = S);
                _ = 1 / 0;
                for (var x = 0; x < 32 && !(g < l); x++) {
                    var b = v - g,
                        w = v + g;
                    d[0] = y(t, r, i, b), d[1] = y(e, n, a, b);
                    var S = o(d, f);
                    if (b >= 0 && S < _) v = b, _ = S;
                    else {
                        p[0] = y(t, r, i, w), p[1] = y(e, n, a, w);
                        var k = o(p, f);
                        w <= 1 && k < _ ? (v = w, _ = k) : g *= .5
                    }
                }
                return c && (c[0] = y(t, r, i, v), c[1] = y(e, n, a, v)), s(_)
            }
        },
        function (t, e, r) {
            var n = r(5),
                i = r(2),
                o = r(84),
                a = r(3),
                s = r(13).devicePixelRatio,
                u = {
                    M: 1,
                    L: 2,
                    C: 3,
                    Q: 4,
                    A: 5,
                    Z: 6,
                    R: 7
                },
                l = [],
                h = [],
                c = [],
                f = [],
                d = Math.min,
                p = Math.max,
                v = Math.cos,
                g = Math.sin,
                _ = Math.sqrt,
                y = Math.abs,
                m = "undefined" != typeof Float32Array,
                x = function (t) {
                    this._saveData = !t, this._saveData && (this.data = []), this._ctx = null
                };
            x.prototype = {
                constructor: x,
                _xi: 0,
                _yi: 0,
                _x0: 0,
                _y0: 0,
                _ux: 0,
                _uy: 0,
                _len: 0,
                _lineDash: null,
                _dashOffset: 0,
                _dashIdx: 0,
                _dashSum: 0,
                setScale: function (t, e) {
                    this._ux = y(1 / s / t) || 0, this._uy = y(1 / s / e) || 0
                }, getContext: function () {
                    return this._ctx
                }, beginPath: function (t) {
                    return this._ctx = t, t && t.beginPath(), t && (this.dpr = t.dpr), this._saveData && (this._len = 0), this._lineDash && (this._lineDash = null, this._dashOffset = 0), this
                }, moveTo: function (t, e) {
                    return this.addData(u.M, t, e), this._ctx && this._ctx.moveTo(t, e), this._x0 = t, this._y0 = e, this._xi = t, this._yi = e, this
                }, lineTo: function (t, e) {
                    var r = y(t - this._xi) > this._ux || y(e - this._yi) > this._uy || this._len < 5;
                    return this.addData(u.L, t, e), this._ctx && r && (this._needsDash() ? this._dashedLineTo(t, e) : this._ctx.lineTo(t, e)), r && (this._xi = t, this._yi = e), this
                }, bezierCurveTo: function (t, e, r, n, i, o) {
                    return this.addData(u.C, t, e, r, n, i, o), this._ctx && (this._needsDash() ? this._dashedBezierTo(t, e, r, n, i, o) : this._ctx.bezierCurveTo(t, e, r, n, i, o)), this._xi = i, this._yi = o, this
                }, quadraticCurveTo: function (t, e, r, n) {
                    return this.addData(u.Q, t, e, r, n), this._ctx && (this._needsDash() ? this._dashedQuadraticTo(t, e, r, n) : this._ctx.quadraticCurveTo(t, e, r, n)), this._xi = r, this._yi = n, this
                }, arc: function (t, e, r, n, i, o) {
                    return this.addData(u.A, t, e, r, r, n, i - n, 0, o ? 0 : 1), this._ctx && this._ctx.arc(t, e, r, n, i, o), this._xi = v(i) * r + t, this._yi = g(i) * r + t, this
                }, arcTo: function (t, e, r, n, i) {
                    return this._ctx && this._ctx.arcTo(t, e, r, n, i), this
                }, rect: function (t, e, r, n) {
                    return this._ctx && this._ctx.rect(t, e, r, n), this.addData(u.R, t, e, r, n), this
                }, closePath: function () {
                    this.addData(u.Z);
                    var t = this._ctx,
                        e = this._x0,
                        r = this._y0;
                    return t && (this._needsDash() && this._dashedLineTo(e, r), t.closePath()), this._xi = e, this._yi = r, this
                }, fill: function (t) {
                    t && t.fill(), this.toStatic()
                }, stroke: function (t) {
                    t && t.stroke(), this.toStatic()
                }, setLineDash: function (t) {
                    if (t instanceof Array) {
                        this._lineDash = t, this._dashIdx = 0;
                        for (var e = 0, r = 0; r < t.length; r++) e += t[r];
                        this._dashSum = e
                    }
                    return this
                }, setLineDashOffset: function (t) {
                    return this._dashOffset = t, this
                }, len: function () {
                    return this._len
                }, setData: function (t) {
                    var e = t.length;
                    this.data && this.data.length == e || !m || (this.data = new Float32Array(e));
                    for (var r = 0; r < e; r++) this.data[r] = t[r];
                    this._len = e
                }, appendPath: function (t) {
                    t instanceof Array || (t = [t]);
                    for (var e = t.length, r = 0, n = this._len, i = 0; i < e; i++) r += t[i].len();
                    m && this.data instanceof Float32Array && (this.data = new Float32Array(n + r));
                    for (i = 0; i < e; i++)
                        for (var o = t[i].data, a = 0; a < o.length; a++) this.data[n++] = o[a];
                    this._len = n
                }, addData: function (t) {
                    if (this._saveData) {
                        var e = this.data;
                        this._len + arguments.length > e.length && (this._expandData(), e = this.data);
                        for (var r = 0; r < arguments.length; r++) e[this._len++] = arguments[r];
                        this._prevCmd = t
                    }
                }, _expandData: function () {
                    if (!(this.data instanceof Array)) {
                        for (var t = [], e = 0; e < this._len; e++) t[e] = this.data[e];
                        this.data = t
                    }
                }, _needsDash: function () {
                    return this._lineDash
                }, _dashedLineTo: function (t, e) {
                    var r, n, i = this._dashSum,
                        o = this._dashOffset,
                        a = this._lineDash,
                        s = this._ctx,
                        u = this._xi,
                        l = this._yi,
                        h = t - u,
                        c = e - l,
                        f = _(h * h + c * c),
                        v = u,
                        g = l,
                        y = a.length;
                    for (h /= f, c /= f, o < 0 && (o = i + o), v -= (o %= i) * h, g -= o * c; h > 0 && v <= t || h < 0 && v >= t || 0 == h && (c > 0 && g <= e || c < 0 && g >= e);) v += h * (r = a[n = this._dashIdx]), g += c * r, this._dashIdx = (n + 1) % y, h > 0 && v < u || h < 0 && v > u || c > 0 && g < l || c < 0 && g > l || s[n % 2 ? "moveTo" : "lineTo"](h >= 0 ? d(v, t) : p(v, t), c >= 0 ? d(g, e) : p(g, e));
                    h = v - t, c = g - e, this._dashOffset = -_(h * h + c * c)
                }, _dashedBezierTo: function (t, e, r, i, o, a) {
                    var s, u, l, h, c, f = this._dashSum,
                        d = this._dashOffset,
                        p = this._lineDash,
                        v = this._ctx,
                        g = this._xi,
                        y = this._yi,
                        m = n.cubicAt,
                        x = 0,
                        b = this._dashIdx,
                        w = p.length,
                        S = 0;
                    for (d < 0 && (d = f + d), d %= f, s = 0; s < 1; s += .1) u = m(g, t, r, o, s + .1) - m(g, t, r, o, s), l = m(y, e, i, a, s + .1) - m(y, e, i, a, s), x += _(u * u + l * l);
                    for (; b < w && !((S += p[b]) > d); b++);
                    for (s = (S - d) / x; s <= 1;) h = m(g, t, r, o, s), c = m(y, e, i, a, s), b % 2 ? v.moveTo(h, c) : v.lineTo(h, c), s += p[b] / x, b = (b + 1) % w;
                    b % 2 != 0 && v.lineTo(o, a), u = o - h, l = a - c, this._dashOffset = -_(u * u + l * l)
                }, _dashedQuadraticTo: function (t, e, r, n) {
                    var i = r,
                        o = n;
                    r = (r + 2 * t) / 3, n = (n + 2 * e) / 3, t = (this._xi + 2 * t) / 3, e = (this._yi + 2 * e) / 3, this._dashedBezierTo(t, e, r, n, i, o)
                }, toStatic: function () {
                    var t = this.data;
                    t instanceof Array && (t.length = this._len, m && (this.data = new Float32Array(t)))
                }, getBoundingRect: function () {
                    l[0] = l[1] = c[0] = c[1] = Number.MAX_VALUE, h[0] = h[1] = f[0] = f[1] = -Number.MAX_VALUE;
                    for (var t = this.data, e = 0, r = 0, n = 0, s = 0, d = 0; d < t.length;) {
                        var p = t[d++];
                        switch (1 == d && (n = e = t[d], s = r = t[d + 1]), p) {
                        case u.M:
                            e = n = t[d++], r = s = t[d++], c[0] = n, c[1] = s, f[0] = n, f[1] = s;
                            break;
                        case u.L:
                            o.fromLine(e, r, t[d], t[d + 1], c, f), e = t[d++], r = t[d++];
                            break;
                        case u.C:
                            o.fromCubic(e, r, t[d++], t[d++], t[d++], t[d++], t[d], t[d + 1], c, f), e = t[d++], r = t[d++];
                            break;
                        case u.Q:
                            o.fromQuadratic(e, r, t[d++], t[d++], t[d], t[d + 1], c, f), e = t[d++], r = t[d++];
                            break;
                        case u.A:
                            var _ = t[d++],
                                y = t[d++],
                                m = t[d++],
                                x = t[d++],
                                b = t[d++],
                                w = t[d++] + b,
                                S = (t[d++], 1 - t[d++]);
                            1 == d && (n = v(b) * m + _, s = g(b) * x + y), o.fromArc(_, y, m, x, b, w, S, c, f), e = v(w) * m + _, r = g(w) * x + y;
                            break;
                        case u.R:
                            n = e = t[d++], s = r = t[d++];
                            var k = t[d++],
                                T = t[d++];
                            o.fromLine(n, s, n + k, s + T, c, f);
                            break;
                        case u.Z:
                            e = n, r = s
                        }
                        i.min(l, l, c), i.max(h, h, f)
                    }
                    return 0 === d && (l[0] = l[1] = h[0] = h[1] = 0), new a(l[0], l[1], h[0] - l[0], h[1] - l[1])
                }, rebuildPath: function (t) {
                    for (var e, r, n, i, o, a, s = this.data, l = this._ux, h = this._uy, c = this._len, f = 0; f < c;) {
                        var d = s[f++];
                        switch (1 == f && (e = n = s[f], r = i = s[f + 1]), d) {
                        case u.M:
                            e = n = s[f++], r = i = s[f++], t.moveTo(n, i);
                            break;
                        case u.L:
                            o = s[f++], a = s[f++], (y(o - n) > l || y(a - i) > h || f === c - 1) && (t.lineTo(o, a), n = o, i = a);
                            break;
                        case u.C:
                            t.bezierCurveTo(s[f++], s[f++], s[f++], s[f++], s[f++], s[f++]), n = s[f - 2], i = s[f - 1];
                            break;
                        case u.Q:
                            t.quadraticCurveTo(s[f++], s[f++], s[f++], s[f++]), n = s[f - 2], i = s[f - 1];
                            break;
                        case u.A:
                            var p = s[f++],
                                _ = s[f++],
                                m = s[f++],
                                x = s[f++],
                                b = s[f++],
                                w = s[f++],
                                S = s[f++],
                                k = s[f++],
                                T = m > x ? m : x,
                                C = m > x ? 1 : m / x,
                                A = m > x ? x / m : 1,
                                L = b + w;
                            Math.abs(m - x) > .001 ? (t.translate(p, _), t.rotate(S), t.scale(C, A), t.arc(0, 0, T, b, L, 1 - k), t.scale(1 / C, 1 / A), t.rotate(-S), t.translate(-p, -_)) : t.arc(p, _, T, b, L, 1 - k), 1 == f && (e = v(b) * m + p, r = g(b) * x + _), n = v(L) * m + p, i = g(L) * x + _;
                            break;
                        case u.R:
                            e = n = s[f], r = i = s[f + 1], t.rect(s[f++], s[f++], s[f++], s[f++]);
                            break;
                        case u.Z:
                            t.closePath(), n = e, i = r
                        }
                    }
                }
            }, x.CMD = u;
            var b = x;
            t.exports = b
        },
        function (t, e, r) {
            var n = r(8),
                i = r(0),
                o = r(11),
                a = r(12),
                s = function (t) {
                    n.call(this, t)
                };
            s.prototype = {
                constructor: s,
                type: "text",
                brush: function (t, e) {
                    var r = this.style;
                    this.__dirty && a.normalizeTextStyle(r, !0), r.fill = r.stroke = r.shadowBlur = r.shadowColor = r.shadowOffsetX = r.shadowOffsetY = null;
                    var n = r.text;
                    null != n && (n += ""), r.bind(t, this, e), a.needDrawText(n, r) && (this.setTransform(t), a.renderText(this, t, n, r), this.restoreTransform(t))
                }, getBoundingRect: function () {
                    var t = this.style;
                    if (this.__dirty && a.normalizeTextStyle(t, !0), !this._rect) {
                        var e = t.text;
                        null != e ? e += "" : e = "";
                        var r = o.getBoundingRect(t.text + "", t.font, t.textAlign, t.textVerticalAlign, t.textPadding, t.rich);
                        if (r.x += t.x || 0, r.y += t.y || 0, a.getStroke(t.textStroke, t.textStrokeWidth)) {
                            var n = t.textStrokeWidth;
                            r.x -= n / 2, r.y -= n / 2, r.width += n, r.height += n
                        }
                        this._rect = r
                    }
                    return this._rect
                }
            }, i.inherits(s, n);
            var u = s;
            t.exports = u
        },
        function (t, e, r) {
            var n = r(0),
                i = r(32),
                o = r(36),
                a = r(29);

            function s(t) {
                for (var e in t = t || {}, o.call(this, t), t) t.hasOwnProperty(e) && "style" !== e && (this[e] = t[e]);
                this.style = new i(t.style, this), this._rect = null, this.__clipPaths = []
            }
            s.prototype = {
                constructor: s,
                type: "displayable",
                __dirty: !0,
                invisible: !1,
                z: 0,
                z2: 0,
                zlevel: 0,
                draggable: !1,
                dragging: !1,
                silent: !1,
                culling: !1,
                cursor: "pointer",
                rectHover: !1,
                progressive: !1,
                incremental: !1,
                inplace: !1,
                beforeBrush: function (t) {}, afterBrush: function (t) {}, brush: function (t, e) {}, getBoundingRect: function () {}, contain: function (t, e) {
                    return this.rectContain(t, e)
                }, traverse: function (t, e) {
                    t.call(e, this)
                }, rectContain: function (t, e) {
                    var r = this.transformCoordToLocal(t, e);
                    return this.getBoundingRect().contain(r[0], r[1])
                }, dirty: function () {
                    this.__dirty = !0, this._rect = null, this.__zr && this.__zr.refresh()
                }, animateStyle: function (t) {
                    return this.animate("style", t)
                }, attrKV: function (t, e) {
                    "style" !== t ? o.prototype.attrKV.call(this, t, e) : this.style.set(e)
                }, setStyle: function (t, e) {
                    return this.style.set(t, e), this.dirty(!1), this
                }, useStyle: function (t) {
                    return this.style = new i(t, this), this.dirty(!1), this
                }
            }, n.inherits(s, o), n.mixin(s, a);
            var u = s;
            t.exports = u
        },
        function (t, e, r) {
            var n = r(8),
                i = r(3),
                o = r(0),
                a = r(21);

            function s(t) {
                n.call(this, t)
            }
            s.prototype = {
                constructor: s,
                type: "image",
                brush: function (t, e) {
                    var r = this.style,
                        n = r.image;
                    r.bind(t, this, e);
                    var i = this._image = a.createOrUpdateImage(n, this._image, this, this.onload);
                    if (i && a.isImageReady(i)) {
                        var o = r.x || 0,
                            s = r.y || 0,
                            u = r.width,
                            l = r.height,
                            h = i.width / i.height;
                        if (null == u && null != l ? u = l * h : null == l && null != u ? l = u / h : null == u && null == l && (u = i.width, l = i.height), this.setTransform(t), r.sWidth && r.sHeight) {
                            var c = r.sx || 0,
                                f = r.sy || 0;
                            t.drawImage(i, c, f, r.sWidth, r.sHeight, o, s, u, l)
                        } else if (r.sx && r.sy) {
                            var d = u - (c = r.sx),
                                p = l - (f = r.sy);
                            t.drawImage(i, c, f, d, p, o, s, u, l)
                        } else t.drawImage(i, o, s, u, l);
                        null != r.text && (this.restoreTransform(t), this.drawRectText(t, this.getBoundingRect()))
                    }
                }, getBoundingRect: function () {
                    var t = this.style;
                    return this._rect || (this._rect = new i(t.x || 0, t.y || 0, t.width || 0, t.height || 0)), this._rect
                }
            }, o.inherits(s, n);
            var u = s;
            t.exports = u
        },
        function (t, e, r) {
            var n = r(13).debugMode,
                i = function () {};
            1 === n ? i = function () {
                for (var t in arguments) throw new Error(arguments[t])
            } : n > 1 && (i = function () {
                for (var t in arguments);
            });
            var o = i;
            t.exports = o
        },
        function (t, e, r) {
            var n = r(3),
                i = r(21),
                o = r(0),
                a = o.getContext,
                s = o.extend,
                u = o.retrieve2,
                l = o.retrieve3,
                h = o.trim,
                c = {},
                f = 0,
                d = 5e3,
                p = /\{([a-zA-Z0-9_]+)\|([^}]*)\}/g,
                v = "12px sans-serif",
                g = {};

            function _(t, e) {
                var r = t + ":" + (e = e || v);
                if (c[r]) return c[r];
                for (var n = (t + "").split("\n"), i = 0, o = 0, a = n.length; o < a; o++) i = Math.max(T(n[o], e).width, i);
                return f > d && (f = 0, c = {}), f++, c[r] = i, i
            }

            function y(t, e, r) {
                return "right" === r ? t -= e : "center" === r && (t -= e / 2), t
            }

            function m(t, e, r) {
                return "middle" === r ? t -= e / 2 : "bottom" === r && (t -= e), t
            }

            function x(t, e, r, n, i) {
                if (!e) return "";
                var o = (t + "").split("\n");
                i = b(e, r, n, i);
                for (var a = 0, s = o.length; a < s; a++) o[a] = w(o[a], i);
                return o.join("\n")
            }

            function b(t, e, r, n) {
                (n = s({}, n)).font = e;
                r = u(r, "...");
                n.maxIterations = u(n.maxIterations, 2);
                var i = n.minChar = u(n.minChar, 0);
                n.cnCharWidth = _("国", e);
                var o = n.ascCharWidth = _("a", e);
                n.placeholder = u(n.placeholder, "");
                for (var a = t = Math.max(0, t - 1), l = 0; l < i && a >= o; l++) a -= o;
                var h = _(r);
                return h > a && (r = "", h = 0), a = t - h, n.ellipsis = r, n.ellipsisWidth = h, n.contentWidth = a, n.containerWidth = t, n
            }

            function w(t, e) {
                var r = e.containerWidth,
                    n = e.font,
                    i = e.contentWidth;
                if (!r) return "";
                var o = _(t, n);
                if (o <= r) return t;
                for (var a = 0;; a++) {
                    if (o <= i || a >= e.maxIterations) {
                        t += e.ellipsis;
                        break
                    }
                    var s = 0 === a ? S(t, i, e.ascCharWidth, e.cnCharWidth) : o > 0 ? Math.floor(t.length * i / o) : 0;
                    o = _(t = t.substr(0, s), n)
                }
                return "" === t && (t = e.placeholder), t
            }

            function S(t, e, r, n) {
                for (var i = 0, o = 0, a = t.length; o < a && i < e; o++) {
                    var s = t.charCodeAt(o);
                    i += 0 <= s && s <= 127 ? r : n
                }
                return o
            }

            function k(t) {
                return _("国", t)
            }

            function T(t, e) {
                return g.measureText(t, e)
            }

            function C(t, e, r, n) {
                null != t && (t += "");
                var i = k(e),
                    o = t ? t.split("\n") : [],
                    a = o.length * i,
                    s = a;
                if (r && (s += r[0] + r[2]), t && n) {
                    var u = n.outerHeight,
                        l = n.outerWidth;
                    if (null != u && s > u) t = "", o = [];
                    else if (null != l)
                        for (var h = b(l - (r ? r[1] + r[3] : 0), e, n.ellipsis, {
                            minChar: n.minChar,
                            placeholder: n.placeholder
                        }), c = 0, f = o.length; c < f; c++) o[c] = w(o[c], h)
                }
                return {
                    lines: o,
                    height: a,
                    outerHeight: s,
                    lineHeight: i
                }
            }

            function A(t, e) {
                var r = {
                    lines: [],
                    width: 0,
                    height: 0
                };
                if (null != t && (t += ""), !t) return r;
                for (var n, o = p.lastIndex = 0; null != (n = p.exec(t));) {
                    var a = n.index;
                    a > o && L(r, t.substring(o, a)), L(r, n[2], n[1]), o = p.lastIndex
                }
                o < t.length && L(r, t.substring(o, t.length));
                var s = r.lines,
                    h = 0,
                    c = 0,
                    f = [],
                    d = e.textPadding,
                    v = e.truncate,
                    g = v && v.outerWidth,
                    y = v && v.outerHeight;
                d && (null != g && (g -= d[1] + d[3]), null != y && (y -= d[0] + d[2]));
                for (var m = 0; m < s.length; m++) {
                    for (var b = s[m], w = 0, S = 0, T = 0; T < b.tokens.length; T++) {
                        var C = (E = b.tokens[T]).styleName && e.rich[E.styleName] || {},
                            A = E.textPadding = C.textPadding,
                            P = E.font = C.font || e.font,
                            M = E.textHeight = u(C.textHeight, k(P));
                        if (A && (M += A[0] + A[2]), E.height = M, E.lineHeight = l(C.textLineHeight, e.textLineHeight, M), E.textAlign = C && C.textAlign || e.textAlign, E.textVerticalAlign = C && C.textVerticalAlign || "middle", null != y && h + E.lineHeight > y) return {
                            lines: [],
                            width: 0,
                            height: 0
                        };
                        E.textWidth = _(E.text, P);
                        var B = C.textWidth,
                            D = null == B || "auto" === B;
                        if ("string" == typeof B && "%" === B.charAt(B.length - 1)) E.percentWidth = B, f.push(E), B = 0;
                        else {
                            if (D) {
                                B = E.textWidth;
                                var z = C.textBackgroundColor,
                                    O = z && z.image;
                                O && (O = i.findExistImage(O), i.isImageReady(O) && (B = Math.max(B, O.width * M / O.height)))
                            }
                            var I = A ? A[1] + A[3] : 0;
                            B += I;
                            var R = null != g ? g - S : null;
                            null != R && R < B && (!D || R < I ? (E.text = "", E.textWidth = B = 0) : (E.text = x(E.text, R - I, P, v.ellipsis, {
                                minChar: v.minChar
                            }), E.textWidth = _(E.text, P), B = E.textWidth + I))
                        }
                        S += E.width = B, C && (w = Math.max(w, E.lineHeight))
                    }
                    b.width = S, b.lineHeight = w, h += w, c = Math.max(c, S)
                }
                r.outerWidth = r.width = u(e.textWidth, c), r.outerHeight = r.height = u(e.textHeight, h), d && (r.outerWidth += d[1] + d[3], r.outerHeight += d[0] + d[2]);
                for (m = 0; m < f.length; m++) {
                    var E, j = (E = f[m]).percentWidth;
                    E.width = parseInt(j, 10) / 100 * c
                }
                return r
            }

            function L(t, e, r) {
                for (var n = "" === e, i = e.split("\n"), o = t.lines, a = 0; a < i.length; a++) {
                    var s = i[a],
                        u = {
                            styleName: r,
                            text: s,
                            isLineHolder: !s && !n
                        };
                    if (a) o.push({
                        tokens: [u]
                    });
                    else {
                        var l = (o[o.length - 1] || (o[0] = {
                                tokens: []
                            })).tokens,
                            h = l.length;
                        1 === h && l[0].isLineHolder ? l[0] = u : (s || !h || n) && l.push(u)
                    }
                }
            }
            g.measureText = function (t, e) {
                var r = a();
                return r.font = e || v, r.measureText(t)
            }, e.DEFAULT_FONT = v, e.$override = function (t, e) {
                g[t] = e
            }, e.getWidth = _, e.getBoundingRect = function (t, e, r, i, o, a, s) {
                return a ? function (t, e, r, i, o, a, s) {
                    var u = A(t, {
                            rich: a,
                            truncate: s,
                            font: e,
                            textAlign: r,
                            textPadding: o
                        }),
                        l = u.outerWidth,
                        h = u.outerHeight,
                        c = y(0, l, r),
                        f = m(0, h, i);
                    return new n(c, f, l, h)
                }(t, e, r, i, o, a, s) : function (t, e, r, i, o, a) {
                    var s = C(t, e, o, a),
                        u = _(t, e);
                    o && (u += o[1] + o[3]);
                    var l = s.outerHeight,
                        h = y(0, u, r),
                        c = m(0, l, i),
                        f = new n(h, c, u, l);
                    return f.lineHeight = s.lineHeight, f
                }(t, e, r, i, o, s)
            }, e.adjustTextX = y, e.adjustTextY = m, e.adjustTextPositionOnRect = function (t, e, r) {
                var n = e.x,
                    i = e.y,
                    o = e.height,
                    a = e.width,
                    s = o / 2,
                    u = "left",
                    l = "top";
                switch (t) {
                case "left":
                    n -= r, i += s, u = "right", l = "middle";
                    break;
                case "right":
                    n += r + a, i += s, l = "middle";
                    break;
                case "top":
                    n += a / 2, i -= r, u = "center", l = "bottom";
                    break;
                case "bottom":
                    n += a / 2, i += o + r, u = "center";
                    break;
                case "inside":
                    n += a / 2, i += s, u = "center", l = "middle";
                    break;
                case "insideLeft":
                    n += r, i += s, l = "middle";
                    break;
                case "insideRight":
                    n += a - r, i += s, u = "right", l = "middle";
                    break;
                case "insideTop":
                    n += a / 2, i += r, u = "center";
                    break;
                case "insideBottom":
                    n += a / 2, i += o - r, u = "center", l = "bottom";
                    break;
                case "insideTopLeft":
                    n += r, i += r;
                    break;
                case "insideTopRight":
                    n += a - r, i += r, u = "right";
                    break;
                case "insideBottomLeft":
                    n += r, i += o - r, l = "bottom";
                    break;
                case "insideBottomRight":
                    n += a - r, i += o - r, u = "right", l = "bottom"
                }
                return {
                    x: n,
                    y: i,
                    textAlign: u,
                    textVerticalAlign: l
                }
            }, e.truncateText = x, e.getLineHeight = k, e.measureText = T, e.parsePlainText = C, e.parseRichText = A, e.makeFont = function (t) {
                var e = (t.fontSize || t.fontFamily) && [t.fontStyle, t.fontWeight, (t.fontSize || 12) + "px", t.fontFamily || "sans-serif"].join(" ");
                return e && h(e) || t.textFont || t.font
            }
        },
        function (t, e, r) {
            var n = r(0),
                i = n.retrieve2,
                o = n.retrieve3,
                a = n.each,
                s = n.normalizeCssArray,
                u = n.isString,
                l = n.isObject,
                h = r(11),
                c = r(28),
                f = r(21),
                d = r(31),
                p = {
                    left: 1,
                    right: 1,
                    center: 1
                },
                v = {
                    top: 1,
                    bottom: 1,
                    middle: 1
                };

            function g(t) {
                if (t) {
                    t.font = h.makeFont(t);
                    var e = t.textAlign;
                    "middle" === e && (e = "center"), t.textAlign = null == e || p[e] ? e : "left";
                    var r = t.textVerticalAlign || t.textBaseline;
                    "center" === r && (r = "middle"), t.textVerticalAlign = null == r || v[r] ? r : "top", t.textPadding && (t.textPadding = s(t.textPadding))
                }
            }

            function _(t, e, r, n, i) {
                if (r && e.textRotation) {
                    var o = e.textOrigin;
                    "center" === o ? (n = r.width / 2 + r.x, i = r.height / 2 + r.y) : o && (n = o[0] + r.x, i = o[1] + r.y), t.translate(n, i), t.rotate(-e.textRotation), t.translate(-n, -i)
                }
            }

            function y(t, e, r, n, a, s, u, l) {
                var c = n.rich[r.styleName] || {},
                    f = r.textVerticalAlign,
                    d = s + a / 2;
                "top" === f ? d = s + r.height / 2 : "bottom" === f && (d = s + a - r.height / 2), !r.isLineHolder && m(c) && x(t, e, c, "right" === l ? u - r.width : "center" === l ? u - r.width / 2 : u, d - r.height / 2, r.width, r.height);
                var p = r.textPadding;
                p && (u = A(u, l, p), d -= r.height / 2 - p[2] - r.textHeight / 2), S(e, "shadowBlur", o(c.textShadowBlur, n.textShadowBlur, 0)), S(e, "shadowColor", c.textShadowColor || n.textShadowColor || "transparent"), S(e, "shadowOffsetX", o(c.textShadowOffsetX, n.textShadowOffsetX, 0)), S(e, "shadowOffsetY", o(c.textShadowOffsetY, n.textShadowOffsetY, 0)), S(e, "textAlign", l), S(e, "textBaseline", "middle"), S(e, "font", r.font || h.DEFAULT_FONT);
                var v = k(c.textStroke || n.textStroke, _),
                    g = T(c.textFill || n.textFill),
                    _ = i(c.textStrokeWidth, n.textStrokeWidth);
                v && (S(e, "lineWidth", _), S(e, "strokeStyle", v), e.strokeText(r.text, u, d)), g && (S(e, "fillStyle", g), e.fillText(r.text, u, d))
            }

            function m(t) {
                return t.textBackgroundColor || t.textBorderWidth && t.textBorderColor
            }

            function x(t, e, r, n, i, o, a) {
                var s = r.textBackgroundColor,
                    h = r.textBorderWidth,
                    d = r.textBorderColor,
                    p = u(s);
                if (S(e, "shadowBlur", r.textBoxShadowBlur || 0), S(e, "shadowColor", r.textBoxShadowColor || "transparent"), S(e, "shadowOffsetX", r.textBoxShadowOffsetX || 0), S(e, "shadowOffsetY", r.textBoxShadowOffsetY || 0), p || h && d) {
                    e.beginPath();
                    var v = r.textBorderRadius;
                    v ? c.buildPath(e, {
                        x: n,
                        y: i,
                        width: o,
                        height: a,
                        r: v
                    }) : e.rect(n, i, o, a), e.closePath()
                }
                if (p) S(e, "fillStyle", s), e.fill();
                else if (l(s)) {
                    var g = s.image;
                    (g = f.createOrUpdateImage(g, null, t, b, s)) && f.isImageReady(g) && e.drawImage(g, n, i, o, a)
                }
                h && d && (S(e, "lineWidth", h), S(e, "strokeStyle", d), e.stroke())
            }

            function b(t, e) {
                e.image = t
            }

            function w(t, e, r) {
                var n = e.x || 0,
                    i = e.y || 0,
                    o = e.textAlign,
                    a = e.textVerticalAlign;
                if (r) {
                    var s = e.textPosition;
                    if (s instanceof Array) n = r.x + C(s[0], r.width), i = r.y + C(s[1], r.height);
                    else {
                        var u = h.adjustTextPositionOnRect(s, r, e.textDistance);
                        n = u.x, i = u.y, o = o || u.textAlign, a = a || u.textVerticalAlign
                    }
                    var l = e.textOffset;
                    l && (n += l[0], i += l[1])
                }
                return {
                    baseX: n,
                    baseY: i,
                    textAlign: o,
                    textVerticalAlign: a
                }
            }

            function S(t, e, r) {
                return t[e] = d(t, e, r), t[e]
            }

            function k(t, e) {
                return null == t || e <= 0 || "transparent" === t || "none" === t ? null : t.image || t.colorStops ? "#000" : t
            }

            function T(t) {
                return null == t || "none" === t ? null : t.image || t.colorStops ? "#000" : t
            }

            function C(t, e) {
                return "string" == typeof t ? t.lastIndexOf("%") >= 0 ? parseFloat(t) / 100 * e : parseFloat(t) : t
            }

            function A(t, e, r) {
                return "right" === e ? t - r[1] : "center" === e ? t + r[3] / 2 - r[1] / 2 : t + r[3]
            }
            e.normalizeTextStyle = function (t) {
                return g(t), a(t.rich, g), t
            }, e.renderText = function (t, e, r, n, i) {
                n.rich ? function (t, e, r, n, i) {
                    var o = t.__textCotentBlock;
                    o && !t.__dirty || (o = t.__textCotentBlock = h.parseRichText(r, n)),
                        function (t, e, r, n, i) {
                            var o = r.width,
                                a = r.outerWidth,
                                s = r.outerHeight,
                                u = n.textPadding,
                                l = w(0, n, i),
                                c = l.baseX,
                                f = l.baseY,
                                d = l.textAlign,
                                p = l.textVerticalAlign;
                            _(e, n, i, c, f);
                            var v = h.adjustTextX(c, a, d),
                                g = h.adjustTextY(f, s, p),
                                b = v,
                                S = g;
                            u && (b += u[3], S += u[0]);
                            var k = b + o;
                            m(n) && x(t, e, n, v, g, a, s);
                            for (var T = 0; T < r.lines.length; T++) {
                                for (var C, A = r.lines[T], L = A.tokens, P = L.length, M = A.lineHeight, B = A.width, D = 0, z = b, O = k, I = P - 1; D < P && (!(C = L[D]).textAlign || "left" === C.textAlign);) y(t, e, C, n, M, S, z, "left"), B -= C.width, z += C.width, D++;
                                for (; I >= 0 && "right" === (C = L[I]).textAlign;) y(t, e, C, n, M, S, O, "right"), B -= C.width, O -= C.width, I--;
                                for (z += (o - (z - b) - (k - O) - B) / 2; D <= I;) C = L[D], y(t, e, C, n, M, S, z + C.width / 2, "center"), z += C.width, D++;
                                S += M
                            }
                        }(t, e, o, n, i)
                }(t, e, r, n, i) : function (t, e, r, n, i) {
                    var o = S(e, "font", n.font || h.DEFAULT_FONT),
                        a = n.textPadding,
                        s = t.__textCotentBlock;
                    s && !t.__dirty || (s = t.__textCotentBlock = h.parsePlainText(r, o, a, n.truncate));
                    var u = s.outerHeight,
                        l = s.lines,
                        c = s.lineHeight,
                        f = w(0, n, i),
                        d = f.baseX,
                        p = f.baseY,
                        v = f.textAlign,
                        g = f.textVerticalAlign;
                    _(e, n, i, d, p);
                    var y = h.adjustTextY(p, u, g),
                        b = d,
                        C = y,
                        L = m(n);
                    if (L || a) {
                        var P = h.getWidth(r, o),
                            M = P;
                        a && (M += a[1] + a[3]);
                        var B = h.adjustTextX(d, M, v);
                        L && x(t, e, n, B, y, M, u), a && (b = A(d, v, a), C += a[0])
                    }
                    S(e, "textAlign", v || "left"), S(e, "textBaseline", "middle"), S(e, "shadowBlur", n.textShadowBlur || 0), S(e, "shadowColor", n.textShadowColor || "transparent"), S(e, "shadowOffsetX", n.textShadowOffsetX || 0), S(e, "shadowOffsetY", n.textShadowOffsetY || 0), C += c / 2;
                    var D = n.textStrokeWidth,
                        z = k(n.textStroke, D),
                        O = T(n.textFill);
                    z && (S(e, "lineWidth", D), S(e, "strokeStyle", z)), O && S(e, "fillStyle", O);
                    for (var I = 0; I < l.length; I++) z && e.strokeText(l[I], b, C), O && e.fillText(l[I], b, C), C += c
                }(t, e, r, n, i)
            }, e.getStroke = k, e.getFill = T, e.needDrawText = function (t, e) {
                return null != t && (t || e.textBackgroundColor || e.textBorderWidth && e.textBorderColor || e.textPadding)
            }
        },
        function (t, e) {
            var r = 1;
            "undefined" != typeof window && (r = Math.max(window.devicePixelRatio || 1, 1));
            var n = r;
            e.debugMode = 0, e.devicePixelRatio = n
        },
        function (t, e) {
            var r = "undefined" == typeof Float32Array ? Array : Float32Array;

            function n() {
                var t = new r(6);
                return i(t), t
            }

            function i(t) {
                return t[0] = 1, t[1] = 0, t[2] = 0, t[3] = 1, t[4] = 0, t[5] = 0, t
            }

            function o(t, e) {
                return t[0] = e[0], t[1] = e[1], t[2] = e[2], t[3] = e[3], t[4] = e[4], t[5] = e[5], t
            }
            e.create = n, e.identity = i, e.copy = o, e.mul = function (t, e, r) {
                var n = e[0] * r[0] + e[2] * r[1],
                    i = e[1] * r[0] + e[3] * r[1],
                    o = e[0] * r[2] + e[2] * r[3],
                    a = e[1] * r[2] + e[3] * r[3],
                    s = e[0] * r[4] + e[2] * r[5] + e[4],
                    u = e[1] * r[4] + e[3] * r[5] + e[5];
                return t[0] = n, t[1] = i, t[2] = o, t[3] = a, t[4] = s, t[5] = u, t
            }, e.translate = function (t, e, r) {
                return t[0] = e[0], t[1] = e[1], t[2] = e[2], t[3] = e[3], t[4] = e[4] + r[0], t[5] = e[5] + r[1], t
            }, e.rotate = function (t, e, r) {
                var n = e[0],
                    i = e[2],
                    o = e[4],
                    a = e[1],
                    s = e[3],
                    u = e[5],
                    l = Math.sin(r),
                    h = Math.cos(r);
                return t[0] = n * h + a * l, t[1] = -n * l + a * h, t[2] = i * h + s * l, t[3] = -i * l + h * s, t[4] = h * o + l * u, t[5] = h * u - l * o, t
            }, e.scale = function (t, e, r) {
                var n = r[0],
                    i = r[1];
                return t[0] = e[0] * n, t[1] = e[1] * i, t[2] = e[2] * n, t[3] = e[3] * i, t[4] = e[4] * n, t[5] = e[5] * i, t
            }, e.invert = function (t, e) {
                var r = e[0],
                    n = e[2],
                    i = e[4],
                    o = e[1],
                    a = e[3],
                    s = e[5],
                    u = r * a - o * n;
                return u ? (u = 1 / u, t[0] = a * u, t[1] = -o * u, t[2] = -n * u, t[3] = r * u, t[4] = (n * s - a * i) * u, t[5] = (o * i - r * s) * u, t) : null
            }, e.clone = function (t) {
                var e = n();
                return o(e, t), e
            }
        },
        function (t, e) {
            var r = Array.prototype.slice,
                n = function () {
                    this._$handlers = {}
                };
            n.prototype = {
                constructor: n,
                one: function (t, e, r) {
                    var n = this._$handlers;
                    if (!e || !t) return this;
                    n[t] || (n[t] = []);
                    for (var i = 0; i < n[t].length; i++)
                        if (n[t][i].h === e) return this;
                    return n[t].push({
                        h: e,
                        one: !0,
                        ctx: r || this
                    }), this
                }, on: function (t, e, r) {
                    var n = this._$handlers;
                    if (!e || !t) return this;
                    n[t] || (n[t] = []);
                    for (var i = 0; i < n[t].length; i++)
                        if (n[t][i].h === e) return this;
                    return n[t].push({
                        h: e,
                        one: !1,
                        ctx: r || this
                    }), this
                }, isSilent: function (t) {
                    var e = this._$handlers;
                    return e[t] && e[t].length
                }, off: function (t, e) {
                    var r = this._$handlers;
                    if (!t) return this._$handlers = {}, this;
                    if (e) {
                        if (r[t]) {
                            for (var n = [], i = 0, o = r[t].length; i < o; i++) r[t][i].h != e && n.push(r[t][i]);
                            r[t] = n
                        }
                        r[t] && 0 === r[t].length && delete r[t]
                    } else delete r[t];
                    return this
                }, trigger: function (t) {
                    if (this._$handlers[t]) {
                        var e = arguments,
                            n = e.length;
                        n > 3 && (e = r.call(e, 1));
                        for (var i = this._$handlers[t], o = i.length, a = 0; a < o;) {
                            switch (n) {
                            case 1:
                                i[a].h.call(i[a].ctx);
                                break;
                            case 2:
                                i[a].h.call(i[a].ctx, e[1]);
                                break;
                            case 3:
                                i[a].h.call(i[a].ctx, e[1], e[2]);
                                break;
                            default:
                                i[a].h.apply(i[a].ctx, e)
                            }
                            i[a].one ? (i.splice(a, 1), o--) : a++
                        }
                    }
                    return this
                }, triggerWithContext: function (t) {
                    if (this._$handlers[t]) {
                        var e = arguments,
                            n = e.length;
                        n > 4 && (e = r.call(e, 1, e.length - 1));
                        for (var i = e[e.length - 1], o = this._$handlers[t], a = o.length, s = 0; s < a;) {
                            switch (n) {
                            case 1:
                                o[s].h.call(i);
                                break;
                            case 2:
                                o[s].h.call(i, e[1]);
                                break;
                            case 3:
                                o[s].h.call(i, e[1], e[2]);
                                break;
                            default:
                                o[s].h.apply(i, e)
                            }
                            o[s].one ? (o.splice(s, 1), a--) : s++
                        }
                    }
                    return this
                }
            };
            var i = n;
            t.exports = i
        },
        function (t, e, r) {
            var n = r(17).createElement,
                i = r(0),
                o = r(1),
                a = r(9),
                s = r(7),
                u = r(18),
                l = u.path,
                h = u.image,
                c = u.text;

            function f(t, e, r, n, i) {
                this._zrId = t, this._svgRoot = e, this._tagNames = "string" == typeof r ? [r] : r, this._markLabel = n, this._domName = i || "_dom", this.nextId = 0
            }
            f.prototype.createElement = n, f.prototype.getDefs = function (t) {
                var e = this._svgRoot,
                    r = this._svgRoot.getElementsByTagName("defs");
                return 0 === r.length ? t ? ((r = e.insertBefore(this.createElement("defs"), e.firstChild)).contains || (r.contains = function (t) {
                    var e = r.children;
                    if (!e) return !1;
                    for (var n = e.length - 1; n >= 0; --n)
                        if (e[n] === t) return !0;
                    return !1
                }), r) : null : r[0]
            }, f.prototype.update = function (t, e) {
                if (t) {
                    var r = this.getDefs(!1);
                    if (t[this._domName] && r.contains(t[this._domName])) "function" == typeof e && e(t);
                    else {
                        var n = this.add(t);
                        n && (t[this._domName] = n)
                    }
                }
            }, f.prototype.addDom = function (t) {
                this.getDefs(!0).appendChild(t)
            }, f.prototype.removeDom = function (t) {
                var e = this.getDefs(!1);
                e && t[this._domName] && (e.removeChild(t[this._domName]), t[this._domName] = null)
            }, f.prototype.getDoms = function () {
                var t = this.getDefs(!1);
                if (!t) return [];
                var e = [];
                return i.each(this._tagNames, function (r) {
                    var n = t.getElementsByTagName(r);
                    e = e.concat([].slice.call(n))
                }), e
            }, f.prototype.markAllUnused = function () {
                var t = this.getDoms(),
                    e = this;
                i.each(t, function (t) {
                    t[e._markLabel] = "0"
                })
            }, f.prototype.markUsed = function (t) {
                t && (t[this._markLabel] = "1")
            }, f.prototype.removeUnused = function () {
                var t = this.getDefs(!1);
                if (t) {
                    var e = this.getDoms(),
                        r = this;
                    i.each(e, function (e) {
                        "1" !== e[r._markLabel] && t.removeChild(e)
                    })
                }
            }, f.prototype.getSvgProxy = function (t) {
                return t instanceof o ? l : t instanceof a ? h : t instanceof s ? c : l
            }, f.prototype.getTextSvgElement = function (t) {
                return t.__textSvgEl
            }, f.prototype.getSvgElement = function (t) {
                return t.__svgEl
            };
            var d = f;
            t.exports = d
        },
        function (t, e) {
            var r = "http://www.w3.org/2000/svg";
            e.createElement = function (t) {
                return document.createElementNS(r, t)
            }
        },
        function (t, e, r) {
            var n = r(17).createElement,
                i = r(6),
                o = r(3),
                a = r(11),
                s = r(12),
                u = r(7),
                l = i.CMD,
                h = Array.prototype.join,
                c = "none",
                f = Math.round,
                d = Math.sin,
                p = Math.cos,
                v = Math.PI,
                g = 2 * Math.PI,
                _ = 180 / v,
                y = 1e-4;

            function m(t) {
                return f(1e4 * t) / 1e4
            }

            function x(t) {
                return t < y && t > -y
            }

            function b(t, e) {
                e && w(t, "transform", "matrix(" + h.call(e, ",") + ")")
            }

            function w(t, e, r) {
                (!r || "linear" !== r.type && "radial" !== r.type) && t.setAttribute(e, r)
            }

            function S(t, e, r) {
                if (function (t, e) {
                    var r = e ? t.textFill : t.fill;
                    return null != r && r !== c
                }(e, r)) {
                    var n = r ? e.textFill : e.fill;
                    n = "transparent" === n ? c : n, "none" !== t.getAttribute("clip-path") && n === c && (n = "rgba(0, 0, 0, 0.002)"), w(t, "fill", n), w(t, "fill-opacity", e.opacity)
                } else w(t, "fill", c); if (function (t, e) {
                    var r = e ? t.textStroke : t.stroke;
                    return null != r && r !== c
                }(e, r)) {
                    var i = r ? e.textStroke : e.stroke;
                    w(t, "stroke", i = "transparent" === i ? c : i), w(t, "stroke-width", (r ? e.textStrokeWidth : e.lineWidth) / (!r && e.strokeNoScale ? e.host.getLineScale() : 1)), w(t, "paint-order", r ? "stroke" : "fill"), w(t, "stroke-opacity", e.opacity), e.lineDash ? (w(t, "stroke-dasharray", e.lineDash.join(",")), w(t, "stroke-dashoffset", f(e.lineDashOffset || 0))) : w(t, "stroke-dasharray", ""), e.lineCap && w(t, "stroke-linecap", e.lineCap), e.lineJoin && w(t, "stroke-linejoin", e.lineJoin), e.miterLimit && w(t, "stroke-miterlimit", e.miterLimit)
                } else w(t, "stroke", c)
            }
            var k = {};
            k.brush = function (t) {
                var e = t.style,
                    r = t.__svgEl;
                r || (r = n("path"), t.__svgEl = r), t.path || t.createPathProxy();
                var i = t.path;
                if (t.__dirtyPath) {
                    i.beginPath(), t.buildPath(i, t.shape), t.__dirtyPath = !1;
                    var o = function (t) {
                        for (var e = [], r = t.data, n = t.len(), i = 0; i < n;) {
                            var o = "",
                                a = 0;
                            switch (r[i++]) {
                            case l.M:
                                o = "M", a = 2;
                                break;
                            case l.L:
                                o = "L", a = 2;
                                break;
                            case l.Q:
                                o = "Q", a = 4;
                                break;
                            case l.C:
                                o = "C", a = 6;
                                break;
                            case l.A:
                                var s = r[i++],
                                    u = r[i++],
                                    h = r[i++],
                                    c = r[i++],
                                    y = r[i++],
                                    b = r[i++],
                                    w = r[i++],
                                    S = r[i++],
                                    k = Math.abs(b),
                                    T = x(k - g) && !x(k),
                                    C = !1;
                                C = k >= g || !x(k) && (b > -v && b < 0 || b > v) == !!S;
                                var A = m(s + h * p(y)),
                                    L = m(u + c * d(y));
                                T && (b = S ? g - 1e-4 : 1e-4 - g, C = !0, 9 === i && e.push("M", A, L));
                                var P = m(s + h * p(y + b)),
                                    M = m(u + c * d(y + b));
                                e.push("A", m(h), m(c), f(w * _), +C, +S, P, M);
                                break;
                            case l.Z:
                                o = "Z";
                                break;
                            case l.R:
                                P = m(r[i++]), M = m(r[i++]);
                                var B = m(r[i++]),
                                    D = m(r[i++]);
                                e.push("M", P, M, "L", P + B, M, "L", P + B, M + D, "L", P, M + D, "L", P, M)
                            }
                            o && e.push(o);
                            for (var z = 0; z < a; z++) e.push(m(r[i++]))
                        }
                        return e.join(" ")
                    }(i);
                    o.indexOf("NaN") < 0 && w(r, "d", o)
                }
                S(r, e), b(r, t.transform), null != e.text && L(t, t.getBoundingRect())
            };
            var T = {
                    brush: function (t) {
                        var e = t.style,
                            r = e.image;
                        r instanceof HTMLImageElement && (r = r.src);
                        if (r) {
                            var i = e.x || 0,
                                o = e.y || 0,
                                a = e.width,
                                s = e.height,
                                u = t.__svgEl;
                            u || (u = n("image"), t.__svgEl = u), r !== t.__imageSrc && (! function (t, e, r) {
                                t.setAttributeNS("http://www.w3.org/1999/xlink", e, r)
                            }(u, "href", r), t.__imageSrc = r), w(u, "width", a), w(u, "height", s), w(u, "x", i), w(u, "y", o), b(u, t.transform), null != e.text && L(t, t.getBoundingRect())
                        }
                    }
                },
                C = {},
                A = new o,
                L = function (t, e, r) {
                    var i = t.style;
                    t.__dirty && s.normalizeTextStyle(i, !0);
                    var o = i.text;
                    if (null != o) {
                        o += "";
                        var l, h, c = t.__textSvgEl;
                        c || (c = n("text"), t.__textSvgEl = c);
                        var f = i.textPosition,
                            d = i.textDistance,
                            p = i.textAlign || "left";
                        "number" == typeof i.fontSize && (i.fontSize += "px");
                        var v = i.font || [i.fontStyle || "", i.fontWeight || "", i.fontSize || "", i.fontFamily || ""].join(" ") || a.DEFAULT_FONT,
                            g = P(i.textVerticalAlign),
                            _ = (r = a.getBoundingRect(o, v, p, g)).lineHeight;
                        if (f instanceof Array) l = e.x + f[0], h = e.y + f[1];
                        else {
                            var y = a.adjustTextPositionOnRect(f, e, d);
                            l = y.x, h = y.y, g = P(y.textVerticalAlign), p = y.textAlign
                        }
                        w(c, "alignment-baseline", g), v && (c.style.font = v);
                        var m = i.textPadding;
                        if (w(c, "x", l), w(c, "y", h), S(c, i, !0), t instanceof u || t.style.transformText) b(c, t.transform);
                        else {
                            if (t.transform) A.copy(e), A.applyTransform(t.transform), e = A;
                            else {
                                var x = t.transformCoordToGlobal(e.x, e.y);
                                e.x = x[0], e.y = x[1]
                            }
                            var k = i.textOrigin;
                            "center" === k ? (l = r.width / 2 + l, h = r.height / 2 + h) : k && (l = k[0] + l, h = k[1] + h), w(c, "transform", "rotate(" + 180 * -i.textRotation / Math.PI + "," + l + "," + h + ")")
                        }
                        var T = o.split("\n"),
                            C = T.length,
                            L = p;
                        "left" === L ? (L = "start", m && (l += m[3])) : "right" === L ? (L = "end", m && (l -= m[1])) : "center" === L && (L = "middle", m && (l += (m[3] - m[1]) / 2));
                        var M = 0;
                        if ("baseline" === g ? (M = -r.height + _, m && (M -= m[2])) : "middle" === g ? (M = (-r.height + _) / 2, m && (h += (m[0] - m[2]) / 2)) : m && (M += m[0]), t.__text !== o || t.__textFont !== v) {
                            var B = t.__tspanList || [];
                            t.__tspanList = B;
                            for (var D = 0; D < C; D++) {
                                (O = B[D]) ? O.innerHTML = "": (O = B[D] = n("tspan"), c.appendChild(O), w(O, "alignment-baseline", g), w(O, "text-anchor", L)), w(O, "x", l), w(O, "y", h + D * _ + M), O.appendChild(document.createTextNode(T[D]))
                            }
                            for (; D < B.length; D++) c.removeChild(B[D]);
                            B.length = C, t.__text = o, t.__textFont = v
                        } else if (t.__tspanList.length) {
                            var z = t.__tspanList.length;
                            for (D = 0; D < z; ++D) {
                                var O;
                                (O = t.__tspanList[D]) && (w(O, "x", l), w(O, "y", h + D * _ + M))
                            }
                        }
                    }
                };

            function P(t) {
                return "middle" === t ? "middle" : "bottom" === t ? "baseline" : "hanging"
            }
            C.drawRectText = L, C.brush = function (t) {
                var e = t.style;
                null != e.text && (e.textPosition = [0, 0], L(t, {
                    x: e.x || 0,
                    y: e.y || 0,
                    width: 0,
                    height: 0
                }, t.getBoundingRect()))
            }, e.path = k, e.image = T, e.text = C
        },
        function (t, e) {
            var r = function (t) {
                this.colorStops = t || []
            };
            r.prototype = {
                constructor: r,
                addColorStop: function (t, e) {
                    this.colorStops.push({
                        offset: t,
                        color: e
                    })
                }
            };
            var n = r;
            t.exports = n
        },
        function (t, e, r) {
            var n = r(15);
            e.Dispatcher = n;
            var i = r(4),
                o = "undefined" != typeof window && !!window.addEventListener,
                a = /^(?:mouse|pointer|contextmenu|drag|drop)|click/;

            function s(t, e, r, n) {
                return r = r || {}, n || !i.canvasSupported ? u(t, e, r) : i.browser.firefox && null != e.layerX && e.layerX !== e.offsetX ? (r.zrX = e.layerX, r.zrY = e.layerY) : null != e.offsetX ? (r.zrX = e.offsetX, r.zrY = e.offsetY) : u(t, e, r), r
            }

            function u(t, e, r) {
                var n = function (t) {
                    return t.getBoundingClientRect ? t.getBoundingClientRect() : {
                        left: 0,
                        top: 0
                    }
                }(t);
                r.zrX = e.clientX - n.left, r.zrY = e.clientY - n.top
            }
            var l = o ? function (t) {
                t.preventDefault(), t.stopPropagation(), t.cancelBubble = !0
            } : function (t) {
                t.returnValue = !1, t.cancelBubble = !0
            };
            e.clientToLocal = s, e.normalizeEvent = function (t, e, r) {
                if (null != (e = e || window.event).zrX) return e;
                var n = e.type;
                if (n && n.indexOf("touch") >= 0) {
                    var i = "touchend" != n ? e.targetTouches[0] : e.changedTouches[0];
                    i && s(t, i, e, r)
                } else s(t, e, e, r), e.zrDelta = e.wheelDelta ? e.wheelDelta / 120 : -(e.detail || 0) / 3;
                var o = e.button;
                return null == e.which && void 0 !== o && a.test(e.type) && (e.which = 1 & o ? 1 : 2 & o ? 3 : 4 & o ? 2 : 0), e
            }, e.addEventListener = function (t, e, r) {
                o ? t.addEventListener(e, r) : t.attachEvent("on" + e, r)
            }, e.removeEventListener = function (t, e, r) {
                o ? t.removeEventListener(e, r) : t.detachEvent("on" + e, r)
            }, e.stop = l, e.notLeftMouse = function (t) {
                return t.which > 1
            }
        },
        function (t, e, r) {
            var n = new(r(34))(50);

            function i() {
                var t = this.__cachedImgObj;
                this.onload = this.__cachedImgObj = null;
                for (var e = 0; e < t.pending.length; e++) {
                    var r = t.pending[e],
                        n = r.cb;
                    n && n(this, r.cbPayload), r.hostEl.dirty()
                }
                t.pending.length = 0
            }

            function o(t) {
                return t && t.width && t.height
            }
            e.findExistImage = function (t) {
                if ("string" == typeof t) {
                    var e = n.get(t);
                    return e && e.image
                }
                return t
            }, e.createOrUpdateImage = function (t, e, r, a, s) {
                if (t) {
                    if ("string" == typeof t) {
                        if (e && e.__zrImageSrc === t || !r) return e;
                        var u = n.get(t),
                            l = {
                                hostEl: r,
                                cb: a,
                                cbPayload: s
                            };
                        return u ? !o(e = u.image) && u.pending.push(l) : (!e && (e = new Image), e.onload = i, n.put(t, e.__cachedImgObj = {
                            image: e,
                            pending: [l]
                        }), e.src = e.__zrImageSrc = t), e
                    }
                    return t
                }
                return e
            }, e.isImageReady = o
        },
        function (t, e) {
            var r = function (t, e) {
                this.image = t, this.repeat = e, this.type = "pattern"
            };
            r.prototype.getCanvasPattern = function (t) {
                return t.createPattern(this.image, this.repeat || "repeat")
            };
            var n = r;
            t.exports = n
        },
        function (t, e, r) {
            var n = {
                transparent: [0, 0, 0, 0],
                aliceblue: [240, 248, 255, 1],
                antiquewhite: [250, 235, 215, 1],
                aqua: [0, 255, 255, 1],
                aquamarine: [127, 255, 212, 1],
                azure: [240, 255, 255, 1],
                beige: [245, 245, 220, 1],
                bisque: [255, 228, 196, 1],
                black: [0, 0, 0, 1],
                blanchedalmond: [255, 235, 205, 1],
                blue: [0, 0, 255, 1],
                blueviolet: [138, 43, 226, 1],
                brown: [165, 42, 42, 1],
                burlywood: [222, 184, 135, 1],
                cadetblue: [95, 158, 160, 1],
                chartreuse: [127, 255, 0, 1],
                chocolate: [210, 105, 30, 1],
                coral: [255, 127, 80, 1],
                cornflowerblue: [100, 149, 237, 1],
                cornsilk: [255, 248, 220, 1],
                crimson: [220, 20, 60, 1],
                cyan: [0, 255, 255, 1],
                darkblue: [0, 0, 139, 1],
                darkcyan: [0, 139, 139, 1],
                darkgoldenrod: [184, 134, 11, 1],
                darkgray: [169, 169, 169, 1],
                darkgreen: [0, 100, 0, 1],
                darkgrey: [169, 169, 169, 1],
                darkkhaki: [189, 183, 107, 1],
                darkmagenta: [139, 0, 139, 1],
                darkolivegreen: [85, 107, 47, 1],
                darkorange: [255, 140, 0, 1],
                darkorchid: [153, 50, 204, 1],
                darkred: [139, 0, 0, 1],
                darksalmon: [233, 150, 122, 1],
                darkseagreen: [143, 188, 143, 1],
                darkslateblue: [72, 61, 139, 1],
                darkslategray: [47, 79, 79, 1],
                darkslategrey: [47, 79, 79, 1],
                darkturquoise: [0, 206, 209, 1],
                darkviolet: [148, 0, 211, 1],
                deeppink: [255, 20, 147, 1],
                deepskyblue: [0, 191, 255, 1],
                dimgray: [105, 105, 105, 1],
                dimgrey: [105, 105, 105, 1],
                dodgerblue: [30, 144, 255, 1],
                firebrick: [178, 34, 34, 1],
                floralwhite: [255, 250, 240, 1],
                forestgreen: [34, 139, 34, 1],
                fuchsia: [255, 0, 255, 1],
                gainsboro: [220, 220, 220, 1],
                ghostwhite: [248, 248, 255, 1],
                gold: [255, 215, 0, 1],
                goldenrod: [218, 165, 32, 1],
                gray: [128, 128, 128, 1],
                green: [0, 128, 0, 1],
                greenyellow: [173, 255, 47, 1],
                grey: [128, 128, 128, 1],
                honeydew: [240, 255, 240, 1],
                hotpink: [255, 105, 180, 1],
                indianred: [205, 92, 92, 1],
                indigo: [75, 0, 130, 1],
                ivory: [255, 255, 240, 1],
                khaki: [240, 230, 140, 1],
                lavender: [230, 230, 250, 1],
                lavenderblush: [255, 240, 245, 1],
                lawngreen: [124, 252, 0, 1],
                lemonchiffon: [255, 250, 205, 1],
                lightblue: [173, 216, 230, 1],
                lightcoral: [240, 128, 128, 1],
                lightcyan: [224, 255, 255, 1],
                lightgoldenrodyellow: [250, 250, 210, 1],
                lightgray: [211, 211, 211, 1],
                lightgreen: [144, 238, 144, 1],
                lightgrey: [211, 211, 211, 1],
                lightpink: [255, 182, 193, 1],
                lightsalmon: [255, 160, 122, 1],
                lightseagreen: [32, 178, 170, 1],
                lightskyblue: [135, 206, 250, 1],
                lightslategray: [119, 136, 153, 1],
                lightslategrey: [119, 136, 153, 1],
                lightsteelblue: [176, 196, 222, 1],
                lightyellow: [255, 255, 224, 1],
                lime: [0, 255, 0, 1],
                limegreen: [50, 205, 50, 1],
                linen: [250, 240, 230, 1],
                magenta: [255, 0, 255, 1],
                maroon: [128, 0, 0, 1],
                mediumaquamarine: [102, 205, 170, 1],
                mediumblue: [0, 0, 205, 1],
                mediumorchid: [186, 85, 211, 1],
                mediumpurple: [147, 112, 219, 1],
                mediumseagreen: [60, 179, 113, 1],
                mediumslateblue: [123, 104, 238, 1],
                mediumspringgreen: [0, 250, 154, 1],
                mediumturquoise: [72, 209, 204, 1],
                mediumvioletred: [199, 21, 133, 1],
                midnightblue: [25, 25, 112, 1],
                mintcream: [245, 255, 250, 1],
                mistyrose: [255, 228, 225, 1],
                moccasin: [255, 228, 181, 1],
                navajowhite: [255, 222, 173, 1],
                navy: [0, 0, 128, 1],
                oldlace: [253, 245, 230, 1],
                olive: [128, 128, 0, 1],
                olivedrab: [107, 142, 35, 1],
                orange: [255, 165, 0, 1],
                orangered: [255, 69, 0, 1],
                orchid: [218, 112, 214, 1],
                palegoldenrod: [238, 232, 170, 1],
                palegreen: [152, 251, 152, 1],
                paleturquoise: [175, 238, 238, 1],
                palevioletred: [219, 112, 147, 1],
                papayawhip: [255, 239, 213, 1],
                peachpuff: [255, 218, 185, 1],
                peru: [205, 133, 63, 1],
                pink: [255, 192, 203, 1],
                plum: [221, 160, 221, 1],
                powderblue: [176, 224, 230, 1],
                purple: [128, 0, 128, 1],
                red: [255, 0, 0, 1],
                rosybrown: [188, 143, 143, 1],
                royalblue: [65, 105, 225, 1],
                saddlebrown: [139, 69, 19, 1],
                salmon: [250, 128, 114, 1],
                sandybrown: [244, 164, 96, 1],
                seagreen: [46, 139, 87, 1],
                seashell: [255, 245, 238, 1],
                sienna: [160, 82, 45, 1],
                silver: [192, 192, 192, 1],
                skyblue: [135, 206, 235, 1],
                slateblue: [106, 90, 205, 1],
                slategray: [112, 128, 144, 1],
                slategrey: [112, 128, 144, 1],
                snow: [255, 250, 250, 1],
                springgreen: [0, 255, 127, 1],
                steelblue: [70, 130, 180, 1],
                tan: [210, 180, 140, 1],
                teal: [0, 128, 128, 1],
                thistle: [216, 191, 216, 1],
                tomato: [255, 99, 71, 1],
                turquoise: [64, 224, 208, 1],
                violet: [238, 130, 238, 1],
                wheat: [245, 222, 179, 1],
                white: [255, 255, 255, 1],
                whitesmoke: [245, 245, 245, 1],
                yellow: [255, 255, 0, 1],
                yellowgreen: [154, 205, 50, 1]
            };

            function i(t) {
                return (t = Math.round(t)) < 0 ? 0 : t > 255 ? 255 : t
            }

            function o(t) {
                return t < 0 ? 0 : t > 1 ? 1 : t
            }

            function a(t) {
                return t.length && "%" === t.charAt(t.length - 1) ? i(parseFloat(t) / 100 * 255) : i(parseInt(t, 10))
            }

            function s(t) {
                return t.length && "%" === t.charAt(t.length - 1) ? o(parseFloat(t) / 100) : o(parseFloat(t))
            }

            function u(t, e, r) {
                return r < 0 ? r += 1 : r > 1 && (r -= 1), 6 * r < 1 ? t + (e - t) * r * 6 : 2 * r < 1 ? e : 3 * r < 2 ? t + (e - t) * (2 / 3 - r) * 6 : t
            }

            function l(t, e, r) {
                return t + (e - t) * r
            }

            function h(t, e, r, n, i) {
                return t[0] = e, t[1] = r, t[2] = n, t[3] = i, t
            }

            function c(t, e) {
                return t[0] = e[0], t[1] = e[1], t[2] = e[2], t[3] = e[3], t
            }
            var f = new(r(34))(20),
                d = null;

            function p(t, e) {
                d && c(d, e), d = f.put(t, d || e.slice())
            }

            function v(t, e) {
                if (t) {
                    e = e || [];
                    var r = f.get(t);
                    if (r) return c(e, r);
                    var i, o = (t += "").replace(/ /g, "").toLowerCase();
                    if (o in n) return c(e, n[o]), p(t, e), e;
                    if ("#" === o.charAt(0)) return 4 === o.length ? (i = parseInt(o.substr(1), 16)) >= 0 && i <= 4095 ? (h(e, (3840 & i) >> 4 | (3840 & i) >> 8, 240 & i | (240 & i) >> 4, 15 & i | (15 & i) << 4, 1), p(t, e), e) : void h(e, 0, 0, 0, 1) : 7 === o.length ? (i = parseInt(o.substr(1), 16)) >= 0 && i <= 16777215 ? (h(e, (16711680 & i) >> 16, (65280 & i) >> 8, 255 & i, 1), p(t, e), e) : void h(e, 0, 0, 0, 1) : void 0;
                    var u = o.indexOf("("),
                        l = o.indexOf(")");
                    if (-1 !== u && l + 1 === o.length) {
                        var d = o.substr(0, u),
                            v = o.substr(u + 1, l - (u + 1)).split(","),
                            _ = 1;
                        switch (d) {
                        case "rgba":
                            if (4 !== v.length) return void h(e, 0, 0, 0, 1);
                            _ = s(v.pop());
                        case "rgb":
                            return 3 !== v.length ? void h(e, 0, 0, 0, 1) : (h(e, a(v[0]), a(v[1]), a(v[2]), _), p(t, e), e);
                        case "hsla":
                            return 4 !== v.length ? void h(e, 0, 0, 0, 1) : (v[3] = s(v[3]), g(v, e), p(t, e), e);
                        case "hsl":
                            return 3 !== v.length ? void h(e, 0, 0, 0, 1) : (g(v, e), p(t, e), e);
                        default:
                            return
                        }
                    }
                    h(e, 0, 0, 0, 1)
                }
            }

            function g(t, e) {
                var r = (parseFloat(t[0]) % 360 + 360) % 360 / 360,
                    n = s(t[1]),
                    o = s(t[2]),
                    a = o <= .5 ? o * (n + 1) : o + n - o * n,
                    l = 2 * o - a;
                return h(e = e || [], i(255 * u(l, a, r + 1 / 3)), i(255 * u(l, a, r)), i(255 * u(l, a, r - 1 / 3)), 1), 4 === t.length && (e[3] = t[3]), e
            }

            function _(t, e, r) {
                if (e && e.length && t >= 0 && t <= 1) {
                    r = r || [];
                    var n = t * (e.length - 1),
                        a = Math.floor(n),
                        s = Math.ceil(n),
                        u = e[a],
                        h = e[s],
                        c = n - a;
                    return r[0] = i(l(u[0], h[0], c)), r[1] = i(l(u[1], h[1], c)), r[2] = i(l(u[2], h[2], c)), r[3] = o(l(u[3], h[3], c)), r
                }
            }
            var y = _;

            function m(t, e, r) {
                if (e && e.length && t >= 0 && t <= 1) {
                    var n = t * (e.length - 1),
                        a = Math.floor(n),
                        s = Math.ceil(n),
                        u = v(e[a]),
                        h = v(e[s]),
                        c = n - a,
                        f = b([i(l(u[0], h[0], c)), i(l(u[1], h[1], c)), i(l(u[2], h[2], c)), o(l(u[3], h[3], c))], "rgba");
                    return r ? {
                        color: f,
                        leftIndex: a,
                        rightIndex: s,
                        value: n
                    } : f
                }
            }
            var x = m;

            function b(t, e) {
                if (t && t.length) {
                    var r = t[0] + "," + t[1] + "," + t[2];
                    return "rgba" !== e && "hsva" !== e && "hsla" !== e || (r += "," + t[3]), e + "(" + r + ")"
                }
            }
            e.parse = v, e.lift = function (t, e) {
                var r = v(t);
                if (r) {
                    for (var n = 0; n < 3; n++) r[n] = e < 0 ? r[n] * (1 - e) | 0 : (255 - r[n]) * e + r[n] | 0, r[n] > 255 ? r[n] = 255 : t[n] < 0 && (r[n] = 0);
                    return b(r, 4 === r.length ? "rgba" : "rgb")
                }
            }, e.toHex = function (t) {
                var e = v(t);
                if (e) return ((1 << 24) + (e[0] << 16) + (e[1] << 8) + +e[2]).toString(16).slice(1)
            }, e.fastLerp = _, e.fastMapToColor = y, e.lerp = m, e.mapToColor = x, e.modifyHSL = function (t, e, r, n) {
                if (t = v(t)) return t = function (t) {
                    if (t) {
                        var e, r, n = t[0] / 255,
                            i = t[1] / 255,
                            o = t[2] / 255,
                            a = Math.min(n, i, o),
                            s = Math.max(n, i, o),
                            u = s - a,
                            l = (s + a) / 2;
                        if (0 === u) e = 0, r = 0;
                        else {
                            r = l < .5 ? u / (s + a) : u / (2 - s - a);
                            var h = ((s - n) / 6 + u / 2) / u,
                                c = ((s - i) / 6 + u / 2) / u,
                                f = ((s - o) / 6 + u / 2) / u;
                            n === s ? e = f - c : i === s ? e = 1 / 3 + h - f : o === s && (e = 2 / 3 + c - h), e < 0 && (e += 1), e > 1 && (e -= 1)
                        }
                        var d = [360 * e, r, l];
                        return null != t[3] && d.push(t[3]), d
                    }
                }(t), null != e && (t[0] = (i = e, (i = Math.round(i)) < 0 ? 0 : i > 360 ? 360 : i)), null != r && (t[1] = s(r)), null != n && (t[2] = s(n)), b(g(t), "rgba");
                var i
            }, e.modifyAlpha = function (t, e) {
                if ((t = v(t)) && null != e) return t[3] = o(e), b(t, "rgba")
            }, e.stringify = b
        },
        function (t, e, r) {
            var n = r(38),
                i = r(4),
                o = r(0),
                a = r(98),
                s = r(96),
                u = r(91),
                l = r(89),
                h = r(88),
                c = !i.canvasSupported,
                f = {
                    canvas: u
                },
                d = {};
            var p = function (t, e, r) {
                r = r || {}, this.dom = e, this.id = t;
                var n = this,
                    u = new s,
                    d = r.renderer;
                if (c) {
                    if (!f.vml) throw new Error("You need to require 'zrender/vml/vml' to support IE8");
                    d = "vml"
                } else d && f[d] || (d = "canvas");
                var p = new f[d](e, u, r, t);
                this.storage = u, this.painter = p;
                var v = i.node || i.worker ? null : new h(p.getViewportRoot());
                this.handler = new a(u, p, v, p.root), this.animation = new l({
                    stage: {
                        update: o.bind(this.flush, this)
                    }
                }), this.animation.start(), this._needsRefresh;
                var g = u.delFromStorage,
                    _ = u.addToStorage;
                u.delFromStorage = function (t) {
                    g.call(u, t), t && t.removeSelfFromZr(n)
                }, u.addToStorage = function (t) {
                    _.call(u, t), t.addSelfToZr(n)
                }
            };
            p.prototype = {
                constructor: p,
                getId: function () {
                    return this.id
                }, add: function (t) {
                    this.storage.addRoot(t), this._needsRefresh = !0
                }, remove: function (t) {
                    this.storage.delRoot(t), this._needsRefresh = !0
                }, configLayer: function (t, e) {
                    this.painter.configLayer && this.painter.configLayer(t, e), this._needsRefresh = !0
                }, setBackgroundColor: function (t) {
                    this.painter.setBackgroundColor && this.painter.setBackgroundColor(t), this._needsRefresh = !0
                }, refreshImmediately: function () {
                    this._needsRefresh = !1, this.painter.refresh(), this._needsRefresh = !1
                }, refresh: function () {
                    this._needsRefresh = !0
                }, flush: function () {
                    var t;
                    this._needsRefresh && (t = !0, this.refreshImmediately()), this._needsRefreshHover && (t = !0, this.refreshHoverImmediately()), t && this.trigger("rendered")
                }, addHover: function (t, e) {
                    this.painter.addHover && (this.painter.addHover(t, e), this.refreshHover())
                }, removeHover: function (t) {
                    this.painter.removeHover && (this.painter.removeHover(t), this.refreshHover())
                }, clearHover: function () {
                    this.painter.clearHover && (this.painter.clearHover(), this.refreshHover())
                }, refreshHover: function () {
                    this._needsRefreshHover = !0
                }, refreshHoverImmediately: function () {
                    this._needsRefreshHover = !1, this.painter.refreshHover && this.painter.refreshHover()
                }, resize: function (t) {
                    t = t || {}, this.painter.resize(t.width, t.height), this.handler.resize()
                }, clearAnimation: function () {
                    this.animation.clear()
                }, getWidth: function () {
                    return this.painter.getWidth()
                }, getHeight: function () {
                    return this.painter.getHeight()
                }, pathToImage: function (t, e) {
                    return this.painter.pathToImage(t, e)
                }, setCursorStyle: function (t) {
                    this.handler.setCursorStyle(t)
                }, findHover: function (t, e) {
                    return this.handler.findHover(t, e)
                }, on: function (t, e, r) {
                    this.handler.on(t, e, r)
                }, off: function (t, e) {
                    this.handler.off(t, e)
                }, trigger: function (t, e) {
                    this.handler.trigger(t, e)
                }, clear: function () {
                    this.storage.delRoot(), this.painter.clear()
                }, dispose: function () {
                    var t;
                    this.animation.stop(), this.clear(), this.storage.dispose(), this.painter.dispose(), this.handler.dispose(), this.animation = this.storage = this.painter = this.handler = null, t = this.id, delete d[t]
                }
            }, e.version = "4.0.3", e.init = function (t, e) {
                var r = new p(n(), t, e);
                return d[r.id] = r, r
            }, e.dispose = function (t) {
                if (t) t.dispose();
                else {
                    for (var e in d) d.hasOwnProperty(e) && d[e].dispose();
                    d = {}
                }
                return this
            }, e.getInstance = function (t) {
                return d[t]
            }, e.registerPainter = function (t, e) {
                f[t] = e
            }
        },
        function (t, e, r) {
            var n, i = r(4),
                o = "urn:schemas-microsoft-com:vml",
                a = "undefined" == typeof window ? null : window,
                s = !1,
                u = a && a.document;
            if (u && !i.canvasSupported) try {
                !u.namespaces.zrvml && u.namespaces.add("zrvml", o), n = function (t) {
                    return u.createElement("<zrvml:" + t + ' class="zrvml">')
                }
            } catch (t) {
                n = function (t) {
                    return u.createElement("<" + t + ' xmlns="' + o + '" class="zrvml">')
                }
            }
            e.doc = u, e.createNode = function (t) {
                return n(t)
            }, e.initVML = function () {
                if (!s && u) {
                    s = !0;
                    var t = u.styleSheets;
                    t.length < 31 ? u.createStyleSheet().addRule(".zrvml", "behavior:url(#default#VML)") : t[0].addRule(".zrvml", "behavior:url(#default#VML)")
                }
            }
        },
        function (t, e, r) {
            var n = r(65),
                i = r(64);
            e.buildPath = function (t, e, r) {
                var o = e.points,
                    a = e.smooth;
                if (o && o.length >= 2) {
                    if (a && "spline" !== a) {
                        var s = i(o, a, r, e.smoothConstraint);
                        t.moveTo(o[0][0], o[0][1]);
                        for (var u = o.length, l = 0; l < (r ? u : u - 1); l++) {
                            var h = s[2 * l],
                                c = s[2 * l + 1],
                                f = o[(l + 1) % u];
                            t.bezierCurveTo(h[0], h[1], c[0], c[1], f[0], f[1])
                        }
                    } else {
                        "spline" === a && (o = n(o, r)), t.moveTo(o[0][0], o[0][1]), l = 1;
                        for (var d = o.length; l < d; l++) t.lineTo(o[l][0], o[l][1])
                    }
                    r && t.closePath()
                }
            }
        },
        function (t, e) {
            var r = 2 * Math.PI;
            e.normalizeRadian = function (t) {
                return (t %= r) < 0 && (t += r), t
            }
        },
        function (t, e) {
            e.buildPath = function (t, e) {
                var r, n, i, o, a, s = e.x,
                    u = e.y,
                    l = e.width,
                    h = e.height,
                    c = e.r;
                l < 0 && (s += l, l = -l), h < 0 && (u += h, h = -h), "number" == typeof c ? r = n = i = o = c : c instanceof Array ? 1 === c.length ? r = n = i = o = c[0] : 2 === c.length ? (r = i = c[0], n = o = c[1]) : 3 === c.length ? (r = c[0], n = o = c[1], i = c[2]) : (r = c[0], n = c[1], i = c[2], o = c[3]) : r = n = i = o = 0, r + n > l && (r *= l / (a = r + n), n *= l / a), i + o > l && (i *= l / (a = i + o), o *= l / a), n + i > h && (n *= h / (a = n + i), i *= h / a), r + o > h && (r *= h / (a = r + o), o *= h / a), t.moveTo(s + r, u), t.lineTo(s + l - n, u), 0 !== n && t.arc(s + l - n, u + n, n, -Math.PI / 2, 0), t.lineTo(s + l, u + h - i), 0 !== i && t.arc(s + l - i, u + h - i, i, 0, Math.PI / 2), t.lineTo(s + o, u + h), 0 !== o && t.arc(s + o, u + h - o, o, Math.PI / 2, Math.PI), t.lineTo(s, u + r), 0 !== r && t.arc(s + r, u + r, r, Math.PI, 1.5 * Math.PI)
            }
        },
        function (t, e, r) {
            var n = r(12),
                i = new(r(3)),
                o = function () {};
            o.prototype = {
                constructor: o,
                drawRectText: function (t, e) {
                    var r = this.style;
                    e = r.textRect || e, this.__dirty && n.normalizeTextStyle(r, !0);
                    var o = r.text;
                    if (null != o && (o += ""), n.needDrawText(o, r)) {
                        t.save();
                        var a = this.transform;
                        r.transformText ? this.setTransform(t) : a && (i.copy(e), i.applyTransform(a), e = i), n.renderText(this, t, o, r, e), t.restore()
                    }
                }
            };
            var a = o;
            t.exports = a
        },
        function (t, e) {
            var r = "undefined" != typeof window && (window.requestAnimationFrame && window.requestAnimationFrame.bind(window) || window.msRequestAnimationFrame && window.msRequestAnimationFrame.bind(window) || window.mozRequestAnimationFrame || window.webkitRequestAnimationFrame) || function (t) {
                setTimeout(t, 16)
            };
            t.exports = r
        },
        function (t, e) {
            var r = {
                shadowBlur: 1,
                shadowOffsetX: 1,
                shadowOffsetY: 1,
                textShadowBlur: 1,
                textShadowOffsetX: 1,
                textShadowOffsetY: 1,
                textBoxShadowBlur: 1,
                textBoxShadowOffsetX: 1,
                textBoxShadowOffsetY: 1
            };
            t.exports = function (t, e, n) {
                return r.hasOwnProperty(e) ? n * t.dpr : n
            }
        },
        function (t, e, r) {
            var n = r(31),
                i = [
                    ["shadowBlur", 0],
                    ["shadowOffsetX", 0],
                    ["shadowOffsetY", 0],
                    ["shadowColor", "#000"],
                    ["lineCap", "butt"],
                    ["lineJoin", "miter"],
                    ["miterLimit", 10]
                ],
                o = function (t, e) {
                    this.extendFrom(t, !1), this.host = e
                };

            function a(t, e, r) {
                var n = null == e.x ? 0 : e.x,
                    i = null == e.x2 ? 1 : e.x2,
                    o = null == e.y ? 0 : e.y,
                    a = null == e.y2 ? 0 : e.y2;
                return e.global || (n = n * r.width + r.x, i = i * r.width + r.x, o = o * r.height + r.y, a = a * r.height + r.y), n = isNaN(n) ? 0 : n, i = isNaN(i) ? 1 : i, o = isNaN(o) ? 0 : o, a = isNaN(a) ? 0 : a, t.createLinearGradient(n, o, i, a)
            }

            function s(t, e, r) {
                var n = r.width,
                    i = r.height,
                    o = Math.min(n, i),
                    a = null == e.x ? .5 : e.x,
                    s = null == e.y ? .5 : e.y,
                    u = null == e.r ? .5 : e.r;
                return e.global || (a = a * n + r.x, s = s * i + r.y, u *= o), t.createRadialGradient(a, s, 0, a, s, u)
            }
            for (var u = o.prototype = {
                constructor: o,
                host: null,
                fill: "#000",
                stroke: null,
                opacity: 1,
                lineDash: null,
                lineDashOffset: 0,
                shadowBlur: 0,
                shadowOffsetX: 0,
                shadowOffsetY: 0,
                lineWidth: 1,
                strokeNoScale: !1,
                text: null,
                font: null,
                textFont: null,
                fontStyle: null,
                fontWeight: null,
                fontSize: null,
                fontFamily: null,
                textTag: null,
                textFill: "#000",
                textStroke: null,
                textWidth: null,
                textHeight: null,
                textStrokeWidth: 0,
                textLineHeight: null,
                textPosition: "inside",
                textRect: null,
                textOffset: null,
                textAlign: null,
                textVerticalAlign: null,
                textDistance: 5,
                textShadowColor: "transparent",
                textShadowBlur: 0,
                textShadowOffsetX: 0,
                textShadowOffsetY: 0,
                textBoxShadowColor: "transparent",
                textBoxShadowBlur: 0,
                textBoxShadowOffsetX: 0,
                textBoxShadowOffsetY: 0,
                transformText: !1,
                textRotation: 0,
                textOrigin: null,
                textBackgroundColor: null,
                textBorderColor: null,
                textBorderWidth: 0,
                textBorderRadius: 0,
                textPadding: null,
                rich: null,
                truncate: null,
                blend: null,
                bind: function (t, e, r) {
                    for (var o = r && r.style, a = !o, s = 0; s < i.length; s++) {
                        var u = i[s],
                            l = u[0];
                        (a || this[l] !== o[l]) && (t[l] = n(t, l, this[l] || u[1]))
                    }
                    if ((a || this.fill !== o.fill) && (t.fillStyle = this.fill), (a || this.stroke !== o.stroke) && (t.strokeStyle = this.stroke), (a || this.opacity !== o.opacity) && (t.globalAlpha = null == this.opacity ? 1 : this.opacity), (a || this.blend !== o.blend) && (t.globalCompositeOperation = this.blend || "source-over"), this.hasStroke()) {
                        var h = this.lineWidth;
                        t.lineWidth = h / (this.strokeNoScale && e && e.getLineScale ? e.getLineScale() : 1)
                    }
                }, hasFill: function () {
                    var t = this.fill;
                    return null != t && "none" !== t
                }, hasStroke: function () {
                    var t = this.stroke;
                    return null != t && "none" !== t && this.lineWidth > 0
                }, extendFrom: function (t, e) {
                    if (t)
                        for (var r in t)!t.hasOwnProperty(r) || !0 !== e && (!1 === e ? this.hasOwnProperty(r) : null == t[r]) || (this[r] = t[r])
                }, set: function (t, e) {
                    "string" == typeof t ? this[t] = e : this.extendFrom(t, !0)
                }, clone: function () {
                    var t = new this.constructor;
                    return t.extendFrom(this, !0), t
                }, getGradient: function (t, e, r) {
                    for (var n = ("radial" === e.type ? s : a)(t, e, r), i = e.colorStops, o = 0; o < i.length; o++) n.addColorStop(i[o].offset, i[o].color);
                    return n
                }
            }, l = 0; l < i.length; l++) {
                var h = i[l];
                h[0] in u || (u[h[0]] = h[1])
            }
            o.getGradient = u.getGradient;
            var c = o;
            t.exports = c
        },
        function (t, e) {
            var r = 32,
                n = 7;

            function i(t, e, r, n) {
                var i = e + 1;
                if (i === r) return 1;
                if (n(t[i++], t[e]) < 0) {
                    for (; i < r && n(t[i], t[i - 1]) < 0;) i++;
                    ! function (t, e, r) {
                        r--;
                        for (; e < r;) {
                            var n = t[e];
                            t[e++] = t[r], t[r--] = n
                        }
                    }(t, e, i)
                } else
                    for (; i < r && n(t[i], t[i - 1]) >= 0;) i++;
                return i - e
            }

            function o(t, e, r, n, i) {
                for (n === e && n++; n < r; n++) {
                    for (var o, a = t[n], s = e, u = n; s < u;) i(a, t[o = s + u >>> 1]) < 0 ? u = o : s = o + 1;
                    var l = n - s;
                    switch (l) {
                    case 3:
                        t[s + 3] = t[s + 2];
                    case 2:
                        t[s + 2] = t[s + 1];
                    case 1:
                        t[s + 1] = t[s];
                        break;
                    default:
                        for (; l > 0;) t[s + l] = t[s + l - 1], l--
                    }
                    t[s] = a
                }
            }

            function a(t, e, r, n, i, o) {
                var a = 0,
                    s = 0,
                    u = 1;
                if (o(t, e[r + i]) > 0) {
                    for (s = n - i; u < s && o(t, e[r + i + u]) > 0;) a = u, (u = 1 + (u << 1)) <= 0 && (u = s);
                    u > s && (u = s), a += i, u += i
                } else {
                    for (s = i + 1; u < s && o(t, e[r + i - u]) <= 0;) a = u, (u = 1 + (u << 1)) <= 0 && (u = s);
                    u > s && (u = s);
                    var l = a;
                    a = i - u, u = i - l
                }
                for (a++; a < u;) {
                    var h = a + (u - a >>> 1);
                    o(t, e[r + h]) > 0 ? a = h + 1 : u = h
                }
                return u
            }

            function s(t, e, r, n, i, o) {
                var a = 0,
                    s = 0,
                    u = 1;
                if (o(t, e[r + i]) < 0) {
                    for (s = i + 1; u < s && o(t, e[r + i - u]) < 0;) a = u, (u = 1 + (u << 1)) <= 0 && (u = s);
                    u > s && (u = s);
                    var l = a;
                    a = i - u, u = i - l
                } else {
                    for (s = n - i; u < s && o(t, e[r + i + u]) >= 0;) a = u, (u = 1 + (u << 1)) <= 0 && (u = s);
                    u > s && (u = s), a += i, u += i
                }
                for (a++; a < u;) {
                    var h = a + (u - a >>> 1);
                    o(t, e[r + h]) < 0 ? u = h : a = h + 1
                }
                return u
            }

            function u(t, e) {
                var r, i, o, u = n,
                    l = 0;
                r = t.length;
                var h = [];

                function c(r) {
                    var c = i[r],
                        f = o[r],
                        d = i[r + 1],
                        p = o[r + 1];
                    o[r] = f + p, r === l - 3 && (i[r + 1] = i[r + 2], o[r + 1] = o[r + 2]), l--;
                    var v = s(t[d], t, c, f, 0, e);
                    c += v, 0 !== (f -= v) && 0 !== (p = a(t[c + f - 1], t, d, p, p - 1, e)) && (f <= p ? function (r, i, o, l) {
                        var c = 0;
                        for (c = 0; c < i; c++) h[c] = t[r + c];
                        var f = 0,
                            d = o,
                            p = r;
                        if (t[p++] = t[d++], 0 == --l) {
                            for (c = 0; c < i; c++) t[p + c] = h[f + c];
                            return
                        }
                        if (1 === i) {
                            for (c = 0; c < l; c++) t[p + c] = t[d + c];
                            return void(t[p + l] = h[f])
                        }
                        var v, g, _, y = u;
                        for (;;) {
                            v = 0, g = 0, _ = !1;
                            do {
                                if (e(t[d], h[f]) < 0) {
                                    if (t[p++] = t[d++], g++, v = 0, 0 == --l) {
                                        _ = !0;
                                        break
                                    }
                                } else if (t[p++] = h[f++], v++, g = 0, 1 == --i) {
                                    _ = !0;
                                    break
                                }
                            } while ((v | g) < y);
                            if (_) break;
                            do {
                                if (0 !== (v = s(t[d], h, f, i, 0, e))) {
                                    for (c = 0; c < v; c++) t[p + c] = h[f + c];
                                    if (p += v, f += v, (i -= v) <= 1) {
                                        _ = !0;
                                        break
                                    }
                                }
                                if (t[p++] = t[d++], 0 == --l) {
                                    _ = !0;
                                    break
                                }
                                if (0 !== (g = a(h[f], t, d, l, 0, e))) {
                                    for (c = 0; c < g; c++) t[p + c] = t[d + c];
                                    if (p += g, d += g, 0 === (l -= g)) {
                                        _ = !0;
                                        break
                                    }
                                }
                                if (t[p++] = h[f++], 1 == --i) {
                                    _ = !0;
                                    break
                                }
                                y--
                            } while (v >= n || g >= n);
                            if (_) break;
                            y < 0 && (y = 0), y += 2
                        }
                        if ((u = y) < 1 && (u = 1), 1 === i) {
                            for (c = 0; c < l; c++) t[p + c] = t[d + c];
                            t[p + l] = h[f]
                        } else {
                            if (0 === i) throw new Error;
                            for (c = 0; c < i; c++) t[p + c] = h[f + c]
                        }
                    }(c, f, d, p) : function (r, i, o, l) {
                        var c = 0;
                        for (c = 0; c < l; c++) h[c] = t[o + c];
                        var f = r + i - 1,
                            d = l - 1,
                            p = o + l - 1,
                            v = 0,
                            g = 0;
                        if (t[p--] = t[f--], 0 == --i) {
                            for (v = p - (l - 1), c = 0; c < l; c++) t[v + c] = h[c];
                            return
                        }
                        if (1 === l) {
                            for (g = (p -= i) + 1, v = (f -= i) + 1, c = i - 1; c >= 0; c--) t[g + c] = t[v + c];
                            return void(t[p] = h[d])
                        }
                        var _ = u;
                        for (;;) {
                            var y = 0,
                                m = 0,
                                x = !1;
                            do {
                                if (e(h[d], t[f]) < 0) {
                                    if (t[p--] = t[f--], y++, m = 0, 0 == --i) {
                                        x = !0;
                                        break
                                    }
                                } else if (t[p--] = h[d--], m++, y = 0, 1 == --l) {
                                    x = !0;
                                    break
                                }
                            } while ((y | m) < _);
                            if (x) break;
                            do {
                                if (0 !== (y = i - s(h[d], t, r, i, i - 1, e))) {
                                    for (i -= y, g = (p -= y) + 1, v = (f -= y) + 1, c = y - 1; c >= 0; c--) t[g + c] = t[v + c];
                                    if (0 === i) {
                                        x = !0;
                                        break
                                    }
                                }
                                if (t[p--] = h[d--], 1 == --l) {
                                    x = !0;
                                    break
                                }
                                if (0 !== (m = l - a(t[f], h, 0, l, l - 1, e))) {
                                    for (l -= m, g = (p -= m) + 1, v = (d -= m) + 1, c = 0; c < m; c++) t[g + c] = h[v + c];
                                    if (l <= 1) {
                                        x = !0;
                                        break
                                    }
                                }
                                if (t[p--] = t[f--], 0 == --i) {
                                    x = !0;
                                    break
                                }
                                _--
                            } while (y >= n || m >= n);
                            if (x) break;
                            _ < 0 && (_ = 0), _ += 2
                        }(u = _) < 1 && (u = 1);
                        if (1 === l) {
                            for (g = (p -= i) + 1, v = (f -= i) + 1, c = i - 1; c >= 0; c--) t[g + c] = t[v + c];
                            t[p] = h[d]
                        } else {
                            if (0 === l) throw new Error;
                            for (v = p - (l - 1), c = 0; c < l; c++) t[v + c] = h[c]
                        }
                    }(c, f, d, p))
                }
                i = [], o = [], this.mergeRuns = function () {
                    for (; l > 1;) {
                        var t = l - 2;
                        if (t >= 1 && o[t - 1] <= o[t] + o[t + 1] || t >= 2 && o[t - 2] <= o[t] + o[t - 1]) o[t - 1] < o[t + 1] && t--;
                        else if (o[t] > o[t + 1]) break;
                        c(t)
                    }
                }, this.forceMergeRuns = function () {
                    for (; l > 1;) {
                        var t = l - 2;
                        t > 0 && o[t - 1] < o[t + 1] && t--, c(t)
                    }
                }, this.pushRun = function (t, e) {
                    i[l] = t, o[l] = e, l += 1
                }
            }
            t.exports = function (t, e, n, a) {
                n || (n = 0), a || (a = t.length);
                var s = a - n;
                if (!(s < 2)) {
                    var l = 0;
                    if (s < r) o(t, n, a, n + (l = i(t, n, a, e)), e);
                    else {
                        var h = new u(t, e),
                            c = function (t) {
                                for (var e = 0; t >= r;) e |= 1 & t, t >>= 1;
                                return t + e
                            }(s);
                        do {
                            if ((l = i(t, n, a, e)) < c) {
                                var f = s;
                                f > c && (f = c), o(t, n, n + f, n + l, e), l = f
                            }
                            h.pushRun(n, l), h.mergeRuns(), s -= l, n += l
                        } while (0 !== s);
                        h.forceMergeRuns()
                    }
                }
            }
        },
        function (t, e) {
            var r = function () {
                    this.head = null, this.tail = null, this._len = 0
                },
                n = r.prototype;
            n.insert = function (t) {
                var e = new i(t);
                return this.insertEntry(e), e
            }, n.insertEntry = function (t) {
                this.head ? (this.tail.next = t, t.prev = this.tail, t.next = null, this.tail = t) : this.head = this.tail = t, this._len++
            }, n.remove = function (t) {
                var e = t.prev,
                    r = t.next;
                e ? e.next = r : this.head = r, r ? r.prev = e : this.tail = e, t.next = t.prev = null, this._len--
            }, n.len = function () {
                return this._len
            }, n.clear = function () {
                this.head = this.tail = null, this._len = 0
            };
            var i = function (t) {
                    this.value = t, this.next, this.prev
                },
                o = function (t) {
                    this._list = new r, this._map = {}, this._maxSize = t || 10, this._lastRemovedEntry = null
                },
                a = o.prototype;
            a.put = function (t, e) {
                var r = this._list,
                    n = this._map,
                    o = null;
                if (null == n[t]) {
                    var a = r.len(),
                        s = this._lastRemovedEntry;
                    if (a >= this._maxSize && a > 0) {
                        var u = r.head;
                        r.remove(u), delete n[u.key], o = u.value, this._lastRemovedEntry = u
                    }
                    s ? s.value = e : s = new i(e), s.key = t, r.insertEntry(s), n[t] = s
                }
                return o
            }, a.get = function (t) {
                var e = this._map[t],
                    r = this._list;
                if (null != e) return e !== r.tail && (r.remove(e), r.insertEntry(e)), e.value
            }, a.clear = function () {
                this._list.clear(), this._map = {}
            };
            var s = o;
            t.exports = s
        },
        function (t, e, r) {
            var n = r(93),
                i = r(23),
                o = r(0).isArrayLike,
                a = Array.prototype.slice;

            function s(t, e) {
                return t[e]
            }

            function u(t, e, r) {
                t[e] = r
            }

            function l(t, e, r) {
                return (e - t) * r + t
            }

            function h(t, e, r) {
                return r > .5 ? e : t
            }

            function c(t, e, r, n, i) {
                var o = t.length;
                if (1 == i)
                    for (var a = 0; a < o; a++) n[a] = l(t[a], e[a], r);
                else {
                    var s = o && t[0].length;
                    for (a = 0; a < o; a++)
                        for (var u = 0; u < s; u++) n[a][u] = l(t[a][u], e[a][u], r)
                }
            }

            function f(t, e, r) {
                var n = t.length,
                    i = e.length;
                if (n !== i)
                    if (n > i) t.length = i;
                    else
                        for (var o = n; o < i; o++) t.push(1 === r ? e[o] : a.call(e[o]));
                var s = t[0] && t[0].length;
                for (o = 0; o < t.length; o++)
                    if (1 === r) isNaN(t[o]) && (t[o] = e[o]);
                    else
                        for (var u = 0; u < s; u++) isNaN(t[o][u]) && (t[o][u] = e[o][u])
            }

            function d(t, e, r) {
                if (t === e) return !0;
                var n = t.length;
                if (n !== e.length) return !1;
                if (1 === r) {
                    for (var i = 0; i < n; i++)
                        if (t[i] !== e[i]) return !1
                } else {
                    var o = t[0].length;
                    for (i = 0; i < n; i++)
                        for (var a = 0; a < o; a++)
                            if (t[i][a] !== e[i][a]) return !1
                }
                return !0
            }

            function p(t, e, r, n, i, o, a, s, u) {
                var l = t.length;
                if (1 == u)
                    for (var h = 0; h < l; h++) s[h] = v(t[h], e[h], r[h], n[h], i, o, a);
                else {
                    var c = t[0].length;
                    for (h = 0; h < l; h++)
                        for (var f = 0; f < c; f++) s[h][f] = v(t[h][f], e[h][f], r[h][f], n[h][f], i, o, a)
                }
            }

            function v(t, e, r, n, i, o, a) {
                var s = .5 * (r - t),
                    u = .5 * (n - e);
                return (2 * (e - r) + s + u) * a + (-3 * (e - r) - 2 * s - u) * o + s * i + e
            }

            function g(t) {
                if (o(t)) {
                    var e = t.length;
                    if (o(t[0])) {
                        for (var r = [], n = 0; n < e; n++) r.push(a.call(t[n]));
                        return r
                    }
                    return a.call(t)
                }
                return t
            }

            function _(t) {
                return t[0] = Math.floor(t[0]), t[1] = Math.floor(t[1]), t[2] = Math.floor(t[2]), "rgba(" + t.join(",") + ")"
            }

            function y(t, e, r, a, s, u) {
                var g = t._getter,
                    y = t._setter,
                    m = "spline" === e,
                    x = a.length;
                if (x) {
                    var b, w = a[0].value,
                        S = o(w),
                        k = !1,
                        T = !1,
                        C = S ? function (t) {
                            var e = t[t.length - 1].value;
                            return o(e && e[0]) ? 2 : 1
                        }(a) : 0;
                    a.sort(function (t, e) {
                        return t.time - e.time
                    }), b = a[x - 1].time;
                    for (var A = [], L = [], P = a[0].value, M = !0, B = 0; B < x; B++) {
                        A.push(a[B].time / b);
                        var D = a[B].value;
                        if (S && d(D, P, C) || !S && D === P || (M = !1), P = D, "string" == typeof D) {
                            var z = i.parse(D);
                            z ? (D = z, k = !0) : T = !0
                        }
                        L.push(D)
                    }
                    if (u || !M) {
                        var O = L[x - 1];
                        for (B = 0; B < x - 1; B++) S ? f(L[B], O, C) : !isNaN(L[B]) || isNaN(O) || T || k || (L[B] = O);
                        S && f(g(t._target, s), O, C);
                        var I, R, E, j, N, F = 0,
                            G = 0;
                        if (k) var W = [0, 0, 0, 0];
                        var H = new n({
                            target: t._target,
                            life: b,
                            loop: t._loop,
                            delay: t._delay,
                            onframe: function (t, e) {
                                var r;
                                if (e < 0) r = 0;
                                else if (e < G) {
                                    for (r = Math.min(F + 1, x - 1); r >= 0 && !(A[r] <= e); r--);
                                    r = Math.min(r, x - 2)
                                } else {
                                    for (r = F; r < x && !(A[r] > e); r++);
                                    r = Math.min(r - 1, x - 2)
                                }
                                F = r, G = e;
                                var n = A[r + 1] - A[r];
                                if (0 !== n)
                                    if (I = (e - A[r]) / n, m)
                                        if (E = L[r], R = L[0 === r ? r : r - 1], j = L[r > x - 2 ? x - 1 : r + 1], N = L[r > x - 3 ? x - 1 : r + 2], S) p(R, E, j, N, I, I * I, I * I * I, g(t, s), C);
                                        else {
                                            if (k) i = p(R, E, j, N, I, I * I, I * I * I, W, 1), i = _(W);
                                            else {
                                                if (T) return h(E, j, I);
                                                i = v(R, E, j, N, I, I * I, I * I * I)
                                            }
                                            y(t, s, i)
                                        } else if (S) c(L[r], L[r + 1], I, g(t, s), C);
                                else {
                                    var i;
                                    if (k) c(L[r], L[r + 1], I, W, 1), i = _(W);
                                    else {
                                        if (T) return h(L[r], L[r + 1], I);
                                        i = l(L[r], L[r + 1], I)
                                    }
                                    y(t, s, i)
                                }
                            }, ondestroy: r
                        });
                        return e && "spline" !== e && (H.easing = e), H
                    }
                }
            }
            var m = function (t, e, r, n) {
                this._tracks = {}, this._target = t, this._loop = e || !1, this._getter = r || s, this._setter = n || u, this._clipCount = 0, this._delay = 0, this._doneList = [], this._onframeList = [], this._clipList = []
            };
            m.prototype = {
                when: function (t, e) {
                    var r = this._tracks;
                    for (var n in e)
                        if (e.hasOwnProperty(n)) {
                            if (!r[n]) {
                                r[n] = [];
                                var i = this._getter(this._target, n);
                                if (null == i) continue;
                                0 !== t && r[n].push({
                                    time: 0,
                                    value: g(i)
                                })
                            }
                            r[n].push({
                                time: t,
                                value: e[n]
                            })
                        }
                    return this
                }, during: function (t) {
                    return this._onframeList.push(t), this
                }, pause: function () {
                    for (var t = 0; t < this._clipList.length; t++) this._clipList[t].pause();
                    this._paused = !0
                }, resume: function () {
                    for (var t = 0; t < this._clipList.length; t++) this._clipList[t].resume();
                    this._paused = !1
                }, isPaused: function () {
                    return !!this._paused
                }, _doneCallback: function () {
                    this._tracks = {}, this._clipList.length = 0;
                    for (var t = this._doneList, e = t.length, r = 0; r < e; r++) t[r].call(this)
                }, start: function (t, e) {
                    var r, n = this,
                        i = 0,
                        o = function () {
                            --i || n._doneCallback()
                        };
                    for (var a in this._tracks)
                        if (this._tracks.hasOwnProperty(a)) {
                            var s = y(this, t, o, this._tracks[a], a, e);
                            s && (this._clipList.push(s), i++, this.animation && this.animation.addClip(s), r = s)
                        }
                    if (r) {
                        var u = r.onframe;
                        r.onframe = function (t, e) {
                            u(t, e);
                            for (var r = 0; r < n._onframeList.length; r++) n._onframeList[r](t, e)
                        }
                    }
                    return i || this._doneCallback(), this
                }, stop: function (t) {
                    for (var e = this._clipList, r = this.animation, n = 0; n < e.length; n++) {
                        var i = e[n];
                        t && i.onframe(this._target, 1), r && r.removeClip(i)
                    }
                    e.length = 0
                }, delay: function (t) {
                    return this._delay = t, this
                }, done: function (t) {
                    return t && this._doneList.push(t), this
                }, getClips: function () {
                    return this._clipList
                }
            };
            var x = m;
            t.exports = x
        },
        function (t, e, r) {
            var n = r(38),
                i = r(15),
                o = r(95),
                a = r(94),
                s = r(0),
                u = function (t) {
                    o.call(this, t), i.call(this, t), a.call(this, t), this.id = t.id || n()
                };
            u.prototype = {
                type: "element",
                name: "",
                __zr: null,
                ignore: !1,
                clipPath: null,
                isGroup: !1,
                drift: function (t, e) {
                    switch (this.draggable) {
                    case "horizontal":
                        e = 0;
                        break;
                    case "vertical":
                        t = 0
                    }
                    var r = this.transform;
                    r || (r = this.transform = [1, 0, 0, 1, 0, 0]), r[4] += t, r[5] += e, this.decomposeTransform(), this.dirty(!1)
                }, beforeUpdate: function () {}, afterUpdate: function () {}, update: function () {
                    this.updateTransform()
                }, traverse: function (t, e) {}, attrKV: function (t, e) {
                    if ("position" === t || "scale" === t || "origin" === t) {
                        if (e) {
                            var r = this[t];
                            r || (r = this[t] = []), r[0] = e[0], r[1] = e[1]
                        }
                    } else this[t] = e
                }, hide: function () {
                    this.ignore = !0, this.__zr && this.__zr.refresh()
                }, show: function () {
                    this.ignore = !1, this.__zr && this.__zr.refresh()
                }, attr: function (t, e) {
                    if ("string" == typeof t) this.attrKV(t, e);
                    else if (s.isObject(t))
                        for (var r in t) t.hasOwnProperty(r) && this.attrKV(r, t[r]);
                    return this.dirty(!1), this
                }, setClipPath: function (t) {
                    var e = this.__zr;
                    e && t.addSelfToZr(e), this.clipPath && this.clipPath !== t && this.removeClipPath(), this.clipPath = t, t.__zr = e, t.__clipTarget = this, this.dirty(!1)
                }, removeClipPath: function () {
                    var t = this.clipPath;
                    t && (t.__zr && t.removeSelfFromZr(t.__zr), t.__zr = null, t.__clipTarget = null, this.clipPath = null, this.dirty(!1))
                }, addSelfToZr: function (t) {
                    this.__zr = t;
                    var e = this.animators;
                    if (e)
                        for (var r = 0; r < e.length; r++) t.animation.addAnimator(e[r]);
                    this.clipPath && this.clipPath.addSelfToZr(t)
                }, removeSelfFromZr: function (t) {
                    this.__zr = null;
                    var e = this.animators;
                    if (e)
                        for (var r = 0; r < e.length; r++) t.animation.removeAnimator(e[r]);
                    this.clipPath && this.clipPath.removeSelfFromZr(t)
                }
            }, s.mixin(u, a), s.mixin(u, o), s.mixin(u, i);
            var l = u;
            t.exports = l
        },
        function (t, e, r) {
            var n = r(0),
                i = r(36),
                o = r(3),
                a = function (t) {
                    for (var e in t = t || {}, i.call(this, t), t) t.hasOwnProperty(e) && (this[e] = t[e]);
                    this._children = [], this.__storage = null, this.__dirty = !0
                };
            a.prototype = {
                constructor: a,
                isGroup: !0,
                type: "group",
                silent: !1,
                children: function () {
                    return this._children.slice()
                }, childAt: function (t) {
                    return this._children[t]
                }, childOfName: function (t) {
                    for (var e = this._children, r = 0; r < e.length; r++)
                        if (e[r].name === t) return e[r]
                }, childCount: function () {
                    return this._children.length
                }, add: function (t) {
                    return t && t !== this && t.parent !== this && (this._children.push(t), this._doAdd(t)), this
                }, addBefore: function (t, e) {
                    if (t && t !== this && t.parent !== this && e && e.parent === this) {
                        var r = this._children,
                            n = r.indexOf(e);
                        n >= 0 && (r.splice(n, 0, t), this._doAdd(t))
                    }
                    return this
                }, _doAdd: function (t) {
                    t.parent && t.parent.remove(t), t.parent = this;
                    var e = this.__storage,
                        r = this.__zr;
                    e && e !== t.__storage && (e.addToStorage(t), t instanceof a && t.addChildrenToStorage(e)), r && r.refresh()
                }, remove: function (t) {
                    var e = this.__zr,
                        r = this.__storage,
                        i = this._children,
                        o = n.indexOf(i, t);
                    return o < 0 ? this : (i.splice(o, 1), t.parent = null, r && (r.delFromStorage(t), t instanceof a && t.delChildrenFromStorage(r)), e && e.refresh(), this)
                }, removeAll: function () {
                    var t, e, r = this._children,
                        n = this.__storage;
                    for (e = 0; e < r.length; e++) t = r[e], n && (n.delFromStorage(t), t instanceof a && t.delChildrenFromStorage(n)), t.parent = null;
                    return r.length = 0, this
                }, eachChild: function (t, e) {
                    for (var r = this._children, n = 0; n < r.length; n++) {
                        var i = r[n];
                        t.call(e, i, n)
                    }
                    return this
                }, traverse: function (t, e) {
                    for (var r = 0; r < this._children.length; r++) {
                        var n = this._children[r];
                        t.call(e, n), "group" === n.type && n.traverse(t, e)
                    }
                    return this
                }, addChildrenToStorage: function (t) {
                    for (var e = 0; e < this._children.length; e++) {
                        var r = this._children[e];
                        t.addToStorage(r), r instanceof a && r.addChildrenToStorage(t)
                    }
                }, delChildrenFromStorage: function (t) {
                    for (var e = 0; e < this._children.length; e++) {
                        var r = this._children[e];
                        t.delFromStorage(r), r instanceof a && r.delChildrenFromStorage(t)
                    }
                }, dirty: function () {
                    return this.__dirty = !0, this.__zr && this.__zr.refresh(), this
                }, getBoundingRect: function (t) {
                    for (var e = null, r = new o(0, 0, 0, 0), n = t || this._children, i = [], a = 0; a < n.length; a++) {
                        var s = n[a];
                        if (!s.ignore && !s.invisible) {
                            var u = s.getBoundingRect(),
                                l = s.getLocalTransform(i);
                            l ? (r.copy(u), r.applyTransform(l), (e = e || r.clone()).union(r)) : (e = e || u.clone()).union(u)
                        }
                    }
                    return e || r
                }
            }, n.inherits(a, i);
            var s = a;
            t.exports = s
        },
        function (t, e) {
            var r = 2311;
            t.exports = function () {
                return r++
            }
        },
        function (t, e, r) {
            var n = r(24);
            ! function () {
                for (var t in n) {
                    if (null == n || !n.hasOwnProperty(t) || "default" === t || "__esModule" === t) return;
                    e[t] = n[t]
                }
            }();
            var i = r(86);
            ! function () {
                for (var t in i) {
                    if (null == i || !i.hasOwnProperty(t) || "default" === t || "__esModule" === t) return;
                    e[t] = i[t]
                }
            }(), r(53), r(47)
        },
        function (t, e, r) {
            Object.defineProperty(e, "__esModule", {
                value: !0
            });
            var n, i = r(39);
            var o = ((n = i) && n.__esModule ? n : {
                default: n
            }).default.Path.extend({
                type: "prow",
                shape: {
                    x: 0,
                    y: 0,
                    width: 0,
                    height: 0,
                    prowWidthRatio: .75,
                    prowMaxWidth: 30,
                    arrow: "R"
                },
                buildPath: function (t, e) {
                    var r = e.x,
                        n = e.y,
                        i = e.width,
                        o = e.height,
                        a = e.arrow,
                        s = Math.min(e.prowMaxWidth, e.prowWidthRatio * i),
                        u = i - s;
                    "R" === a ? (t.moveTo(r, n), t.lineTo(r, n + o), t.lineTo(r + u, n + o), t.lineTo(r + i, n + o / 2), t.lineTo(r + u, n)) : (t.moveTo(r + i, n), t.lineTo(r + i, n + o), t.lineTo(r + s, n + o), t.lineTo(r, n + o / 2), t.lineTo(r + s, n)), t.closePath()
                }
            });
            e.default = o, t.exports = e.default
        },
        function (e, r) {
            e.exports = t
        },
        function (t, e) {
            for (var r = [], n = 0; n < 256; ++n) r[n] = (n + 256).toString(16).substr(1);
            t.exports = function (t, e) {
                var n = e || 0,
                    i = r;
                return i[t[n++]] + i[t[n++]] + i[t[n++]] + i[t[n++]] + "-" + i[t[n++]] + i[t[n++]] + "-" + i[t[n++]] + i[t[n++]] + "-" + i[t[n++]] + i[t[n++]] + "-" + i[t[n++]] + i[t[n++]] + i[t[n++]] + i[t[n++]] + i[t[n++]] + i[t[n++]]
            }
        },
        function (t, e) {
            var r = "undefined" != typeof crypto && crypto.getRandomValues.bind(crypto) || "undefined" != typeof msCrypto && msCrypto.getRandomValues.bind(msCrypto);
            if (r) {
                var n = new Uint8Array(16);
                t.exports = function () {
                    return r(n), n
                }
            } else {
                var i = new Array(16);
                t.exports = function () {
                    for (var t, e = 0; e < 16; e++) 0 == (3 & e) && (t = 4294967296 * Math.random()), i[e] = t >>> ((3 & e) << 3) & 255;
                    return i
                }
            }
        },
        function (t, e, r) {
            var n, i, o = r(43),
                a = r(42),
                s = 0,
                u = 0;
            t.exports = function (t, e, r) {
                var l = e && r || 0,
                    h = e || [],
                    c = (t = t || {}).node || n,
                    f = void 0 !== t.clockseq ? t.clockseq : i;
                if (null == c || null == f) {
                    var d = o();
                    null == c && (c = n = [1 | d[0], d[1], d[2], d[3], d[4], d[5]]), null == f && (f = i = 16383 & (d[6] << 8 | d[7]))
                }
                var p = void 0 !== t.msecs ? t.msecs : (new Date).getTime(),
                    v = void 0 !== t.nsecs ? t.nsecs : u + 1,
                    g = p - s + (v - u) / 1e4;
                if (g < 0 && void 0 === t.clockseq && (f = f + 1 & 16383), (g < 0 || p > s) && void 0 === t.nsecs && (v = 0), v >= 1e4) throw new Error("uuid.v1(): Can't create more than 10M uuids/sec");
                s = p, u = v, i = f;
                var _ = (1e4 * (268435455 & (p += 122192928e5)) + v) % 4294967296;
                h[l++] = _ >>> 24 & 255, h[l++] = _ >>> 16 & 255, h[l++] = _ >>> 8 & 255, h[l++] = 255 & _;
                var y = p / 4294967296 * 1e4 & 268435455;
                h[l++] = y >>> 8 & 255, h[l++] = 255 & y, h[l++] = y >>> 24 & 15 | 16, h[l++] = y >>> 16 & 255, h[l++] = f >>> 8 | 128, h[l++] = 255 & f;
                for (var m = 0; m < 6; ++m) h[l + m] = c[m];
                return e || a(h)
            }
        },
        function (t, e, r) {
            var n = r(10),
                i = r(25),
                o = r(0).each;

            function a(t) {
                return parseInt(t, 10)
            }

            function s(t, e) {
                i.initVML(), this.root = t, this.storage = e;
                var r = document.createElement("div"),
                    n = document.createElement("div");
                r.style.cssText = "display:inline-block;overflow:hidden;position:relative;width:300px;height:150px;", n.style.cssText = "position:absolute;left:0;top:0;", t.appendChild(r), this._vmlRoot = n, this._vmlViewport = r, this.resize();
                var o = e.delFromStorage,
                    a = e.addToStorage;
                e.delFromStorage = function (t) {
                    o.call(e, t), t && t.onRemove && t.onRemove(n)
                }, e.addToStorage = function (t) {
                    t.onAdd && t.onAdd(n), a.call(e, t)
                }, this._firstPaint = !0
            }
            s.prototype = {
                constructor: s,
                getType: function () {
                    return "vml"
                }, getViewportRoot: function () {
                    return this._vmlViewport
                }, getViewportRootOffset: function () {
                    var t = this.getViewportRoot();
                    if (t) return {
                        offsetLeft: t.offsetLeft || 0,
                        offsetTop: t.offsetTop || 0
                    }
                }, refresh: function () {
                    var t = this.storage.getDisplayList(!0, !0);
                    this._paintList(t)
                }, _paintList: function (t) {
                    for (var e = this._vmlRoot, r = 0; r < t.length; r++) {
                        var n = t[r];
                        n.invisible || n.ignore ? (n.__alreadyNotVisible || n.onRemove(e), n.__alreadyNotVisible = !0) : (n.__alreadyNotVisible && n.onAdd(e), n.__alreadyNotVisible = !1, n.__dirty && (n.beforeBrush && n.beforeBrush(), (n.brushVML || n.brush).call(n, e), n.afterBrush && n.afterBrush())), n.__dirty = !1
                    }
                    this._firstPaint && (this._vmlViewport.appendChild(e), this._firstPaint = !1)
                }, resize: function (t, e) {
                    t = null == t ? this._getWidth() : t, e = null == e ? this._getHeight() : e;
                    if (this._width != t || this._height != e) {
                        this._width = t, this._height = e;
                        var r = this._vmlViewport.style;
                        r.width = t + "px", r.height = e + "px"
                    }
                }, dispose: function () {
                    this.root.innerHTML = "", this._vmlRoot = this._vmlViewport = this.storage = null
                }, getWidth: function () {
                    return this._width
                }, getHeight: function () {
                    return this._height
                }, clear: function () {
                    this._vmlViewport && this.root.removeChild(this._vmlViewport)
                }, _getWidth: function () {
                    var t = this.root,
                        e = t.currentStyle;
                    return (t.clientWidth || a(e.width)) - a(e.paddingLeft) - a(e.paddingRight) | 0
                }, _getHeight: function () {
                    var t = this.root,
                        e = t.currentStyle;
                    return (t.clientHeight || a(e.height)) - a(e.paddingTop) - a(e.paddingBottom) | 0
                }
            }, o(["getLayer", "insertLayer", "eachLayer", "eachBuiltinLayer", "eachOtherLayer", "getLayers", "modLayer", "delLayer", "clearLayer", "toDataURL", "pathToImage"], function (t) {
                var e;
                s.prototype[t] = (e = t, function () {
                    n('In IE8.0 VML mode painter not support method "' + e + '"')
                })
            });
            var u = s;
            t.exports = u
        },
        function (t, e, r) {
            var n = r(4),
                i = r(2).applyTransform,
                o = r(3),
                a = r(23),
                s = r(11),
                u = r(12),
                l = r(29),
                h = r(8),
                c = r(9),
                f = r(7),
                d = r(1),
                p = r(6),
                v = r(19),
                g = r(25),
                _ = p.CMD,
                y = Math.round,
                m = Math.sqrt,
                x = Math.abs,
                b = Math.cos,
                w = Math.sin,
                S = Math.max;
            if (!n.canvasSupported) {
                var k = 21600,
                    T = k / 2,
                    C = function (t) {
                        t.style.cssText = "position:absolute;left:0;top:0;width:1px;height:1px;", t.coordsize = k + "," + k, t.coordorigin = "0,0"
                    },
                    A = function (t, e, r) {
                        return "rgb(" + [t, e, r].join(",") + ")"
                    },
                    L = function (t, e) {
                        e && t && e.parentNode !== t && t.appendChild(e)
                    },
                    P = function (t, e) {
                        e && t && e.parentNode === t && t.removeChild(e)
                    },
                    M = function (t, e, r) {
                        return 1e5 * (parseFloat(t) || 0) + 1e3 * (parseFloat(e) || 0) + r
                    },
                    B = function (t, e) {
                        return "string" == typeof t ? t.lastIndexOf("%") >= 0 ? parseFloat(t) / 100 * e : parseFloat(t) : t
                    },
                    D = function (t, e, r) {
                        var n = a.parse(e);
                        r = +r, isNaN(r) && (r = 1), n && (t.color = A(n[0], n[1], n[2]), t.opacity = r * n[3])
                    },
                    z = function (t, e, r, n) {
                        var o = "fill" == e,
                            s = t.getElementsByTagName(e)[0];
                        null != r[e] && "none" !== r[e] && (o || !o && r.lineWidth) ? (t[o ? "filled" : "stroked"] = "true", r[e] instanceof v && P(t, s), s || (s = g.createNode(e)), o ? function (t, e, r) {
                            var n, o, s = e.fill;
                            if (null != s)
                                if (s instanceof v) {
                                    var u, l = 0,
                                        h = [0, 0],
                                        c = 0,
                                        f = 1,
                                        d = r.getBoundingRect(),
                                        p = d.width,
                                        g = d.height;
                                    if ("linear" === s.type) {
                                        u = "gradient";
                                        var _ = r.transform,
                                            y = [s.x * p, s.y * g],
                                            m = [s.x2 * p, s.y2 * g];
                                        _ && (i(y, y, _), i(m, m, _));
                                        var x = m[0] - y[0],
                                            b = m[1] - y[1];
                                        (l = 180 * Math.atan2(x, b) / Math.PI) < 0 && (l += 360), l < 1e-6 && (l = 0)
                                    } else {
                                        u = "gradientradial", y = [s.x * p, s.y * g], _ = r.transform;
                                        var w = r.scale,
                                            T = p,
                                            C = g;
                                        h = [(y[0] - d.x) / T, (y[1] - d.y) / C], _ && i(y, y, _), T /= w[0] * k, C /= w[1] * k;
                                        var L = S(T, C);
                                        c = 0 / L, f = 2 * s.r / L - c
                                    }
                                    var P = s.colorStops.slice();
                                    P.sort(function (t, e) {
                                        return t.offset - e.offset
                                    });
                                    for (var M = P.length, B = [], z = [], O = 0; O < M; O++) {
                                        var I = P[O],
                                            R = (n = I.color, o = a.parse(n), [A(o[0], o[1], o[2]), o[3]]);
                                        z.push(I.offset * f + c + " " + R[0]), 0 !== O && O !== M - 1 || B.push(R)
                                    }
                                    if (M >= 2) {
                                        var E = B[0][0],
                                            j = B[1][0],
                                            N = B[0][1] * e.opacity,
                                            F = B[1][1] * e.opacity;
                                        t.type = u, t.method = "none", t.focus = "100%", t.angle = l, t.color = E, t.color2 = j, t.colors = z.join(","), t.opacity = F, t.opacity2 = N
                                    }
                                    "radial" === u && (t.focusposition = h.join(","))
                                } else D(t, s, e.opacity)
                        }(s, r, n) : function (t, e) {
                            null != e.lineDash && (t.dashstyle = e.lineDash.join(" ")), null == e.stroke || e.stroke instanceof v || D(t, e.stroke, e.opacity)
                        }(s, r), L(t, s)) : (t[o ? "filled" : "stroked"] = "false", P(t, s))
                    },
                    O = [
                        [],
                        [],
                        []
                    ];
                d.prototype.brushVML = function (t) {
                    var e = this.style,
                        r = this._vmlEl;
                    r || (r = g.createNode("shape"), C(r), this._vmlEl = r), z(r, "fill", e, this), z(r, "stroke", e, this);
                    var n = this.transform,
                        o = null != n,
                        a = r.getElementsByTagName("stroke")[0];
                    if (a) {
                        var s = e.lineWidth;
                        if (o && !e.strokeNoScale) {
                            var u = n[0] * n[3] - n[1] * n[2];
                            s *= m(x(u))
                        }
                        a.weight = s + "px"
                    }
                    var l = this.path || (this.path = new p);
                    this.__dirtyPath && (l.beginPath(), this.buildPath(l, this.shape), l.toStatic(), this.__dirtyPath = !1), r.path = function (t, e) {
                        var r, n, o, a, s, u, l = _.M,
                            h = _.C,
                            c = _.L,
                            f = _.A,
                            d = _.Q,
                            p = [],
                            v = t.data,
                            g = t.len();
                        for (a = 0; a < g;) {
                            switch (n = "", r = 0, o = v[a++]) {
                            case l:
                                n = " m ", r = 1, s = v[a++], u = v[a++], O[0][0] = s, O[0][1] = u;
                                break;
                            case c:
                                n = " l ", r = 1, s = v[a++], u = v[a++], O[0][0] = s, O[0][1] = u;
                                break;
                            case d:
                            case h:
                                n = " c ", r = 3;
                                var x, S, C = v[a++],
                                    A = v[a++],
                                    L = v[a++],
                                    P = v[a++];
                                o === d ? (x = L, S = P, L = (L + 2 * C) / 3, P = (P + 2 * A) / 3, C = (s + 2 * C) / 3, A = (u + 2 * A) / 3) : (x = v[a++], S = v[a++]), O[0][0] = C, O[0][1] = A, O[1][0] = L, O[1][1] = P, O[2][0] = x, O[2][1] = S, s = x, u = S;
                                break;
                            case f:
                                var M = 0,
                                    B = 0,
                                    D = 1,
                                    z = 1,
                                    I = 0;
                                e && (M = e[4], B = e[5], D = m(e[0] * e[0] + e[1] * e[1]), z = m(e[2] * e[2] + e[3] * e[3]), I = Math.atan2(-e[1] / z, e[0] / D));
                                var R = v[a++],
                                    E = v[a++],
                                    j = v[a++],
                                    N = v[a++],
                                    F = v[a++] + I,
                                    G = v[a++] + F + I;
                                a++;
                                var W = v[a++],
                                    H = R + b(F) * j,
                                    X = E + w(F) * N,
                                    q = (C = R + b(G) * j, A = E + w(G) * N, W ? " wa " : " at ");
                                Math.abs(H - C) < 1e-4 && (Math.abs(G - F) > .01 ? W && (H += .0125) : Math.abs(X - E) < 1e-4 ? W && H < R || !W && H > R ? A -= .0125 : A += .0125 : W && X < E || !W && X > E ? C += .0125 : C -= .0125), p.push(q, y(((R - j) * D + M) * k - T), ",", y(((E - N) * z + B) * k - T), ",", y(((R + j) * D + M) * k - T), ",", y(((E + N) * z + B) * k - T), ",", y((H * D + M) * k - T), ",", y((X * z + B) * k - T), ",", y((C * D + M) * k - T), ",", y((A * z + B) * k - T)), s = C, u = A;
                                break;
                            case _.R:
                                var U = O[0],
                                    Y = O[1];
                                U[0] = v[a++], U[1] = v[a++], Y[0] = U[0] + v[a++], Y[1] = U[1] + v[a++], e && (i(U, U, e), i(Y, Y, e)), U[0] = y(U[0] * k - T), Y[0] = y(Y[0] * k - T), U[1] = y(U[1] * k - T), Y[1] = y(Y[1] * k - T), p.push(" m ", U[0], ",", U[1], " l ", Y[0], ",", U[1], " l ", Y[0], ",", Y[1], " l ", U[0], ",", Y[1]);
                                break;
                            case _.Z:
                                p.push(" x ")
                            }
                            if (r > 0) {
                                p.push(n);
                                for (var V = 0; V < r; V++) {
                                    var $ = O[V];
                                    e && i($, $, e), p.push(y($[0] * k - T), ",", y($[1] * k - T), V < r - 1 ? "," : "")
                                }
                            }
                        }
                        return p.join("")
                    }(l, this.transform), r.style.zIndex = M(this.zlevel, this.z, this.z2), L(t, r), null != e.text ? this.drawRectText(t, this.getBoundingRect()) : this.removeRectText(t)
                }, d.prototype.onRemove = function (t) {
                    P(t, this._vmlEl), this.removeRectText(t)
                }, d.prototype.onAdd = function (t) {
                    L(t, this._vmlEl), this.appendRectText(t)
                };
                c.prototype.brushVML = function (t) {
                    var e, r, n, o = this.style,
                        a = o.image;
                    if ("object" == typeof (n = a) && n.tagName && "IMG" === n.tagName.toUpperCase()) {
                        var s = a.src;
                        if (s === this._imageSrc) e = this._imageWidth, r = this._imageHeight;
                        else {
                            var u = a.runtimeStyle,
                                l = u.width,
                                h = u.height;
                            u.width = "auto", u.height = "auto", e = a.width, r = a.height, u.width = l, u.height = h, this._imageSrc = s, this._imageWidth = e, this._imageHeight = r
                        }
                        a = s
                    } else a === this._imageSrc && (e = this._imageWidth, r = this._imageHeight); if (a) {
                        var c = o.x || 0,
                            f = o.y || 0,
                            d = o.width,
                            p = o.height,
                            v = o.sWidth,
                            _ = o.sHeight,
                            x = o.sx || 0,
                            b = o.sy || 0,
                            w = v && _,
                            k = this._vmlEl;
                        k || (k = g.doc.createElement("div"), C(k), this._vmlEl = k);
                        var T, A = k.style,
                            P = !1,
                            B = 1,
                            D = 1;
                        if (this.transform && (T = this.transform, B = m(T[0] * T[0] + T[1] * T[1]), D = m(T[2] * T[2] + T[3] * T[3]), P = T[1] || T[2]), P) {
                            var z = [c, f],
                                O = [c + d, f],
                                I = [c, f + p],
                                R = [c + d, f + p];
                            i(z, z, T), i(O, O, T), i(I, I, T), i(R, R, T);
                            var E = S(z[0], O[0], I[0], R[0]),
                                j = S(z[1], O[1], I[1], R[1]),
                                N = [];
                            N.push("M11=", T[0] / B, ",", "M12=", T[2] / D, ",", "M21=", T[1] / B, ",", "M22=", T[3] / D, ",", "Dx=", y(c * B + T[4]), ",", "Dy=", y(f * D + T[5])), A.padding = "0 " + y(E) + "px " + y(j) + "px 0", A.filter = "progid:DXImageTransform.Microsoft.Matrix(" + N.join("") + ", SizingMethod=clip)"
                        } else T && (c = c * B + T[4], f = f * D + T[5]), A.filter = "", A.left = y(c) + "px", A.top = y(f) + "px";
                        var F = this._imageEl,
                            G = this._cropEl;
                        F || (F = g.doc.createElement("div"), this._imageEl = F);
                        var W = F.style;
                        if (w) {
                            if (e && r) W.width = y(B * e * d / v) + "px", W.height = y(D * r * p / _) + "px";
                            else {
                                var H = new Image,
                                    X = this;
                                H.onload = function () {
                                    H.onload = null, e = H.width, r = H.height, W.width = y(B * e * d / v) + "px", W.height = y(D * r * p / _) + "px", X._imageWidth = e, X._imageHeight = r, X._imageSrc = a
                                }, H.src = a
                            }
                            G || ((G = g.doc.createElement("div")).style.overflow = "hidden", this._cropEl = G);
                            var q = G.style;
                            q.width = y((d + x * d / v) * B), q.height = y((p + b * p / _) * D), q.filter = "progid:DXImageTransform.Microsoft.Matrix(Dx=" + -x * d / v * B + ",Dy=" + -b * p / _ * D + ")", G.parentNode || k.appendChild(G), F.parentNode != G && G.appendChild(F)
                        } else W.width = y(B * d) + "px", W.height = y(D * p) + "px", k.appendChild(F), G && G.parentNode && (k.removeChild(G), this._cropEl = null);
                        var U = "",
                            Y = o.opacity;
                        Y < 1 && (U += ".Alpha(opacity=" + y(100 * Y) + ") "), U += "progid:DXImageTransform.Microsoft.AlphaImageLoader(src=" + a + ", SizingMethod=scale)", W.filter = U, k.style.zIndex = M(this.zlevel, this.z, this.z2), L(t, k), null != o.text && this.drawRectText(t, this.getBoundingRect())
                    }
                }, c.prototype.onRemove = function (t) {
                    P(t, this._vmlEl), this._vmlEl = null, this._cropEl = null, this._imageEl = null, this.removeRectText(t)
                }, c.prototype.onAdd = function (t) {
                    L(t, this._vmlEl), this.appendRectText(t)
                };
                var I, R = {},
                    E = 0,
                    j = document.createElement("div");
                s.$override("measureText", function (t, e) {
                    var r = g.doc;
                    I || ((I = r.createElement("div")).style.cssText = "position:absolute;top:-20000px;left:0;padding:0;margin:0;border:none;white-space:pre;", g.doc.body.appendChild(I));
                    try {
                        I.style.font = e
                    } catch (t) {}
                    return I.innerHTML = "", I.appendChild(r.createTextNode(t)), {
                        width: I.offsetWidth
                    }
                });
                for (var N = new o, F = function (t, e, r, n) {
                    var o = this.style;
                    this.__dirty && u.normalizeTextStyle(o, !0);
                    var a = o.text;
                    if (null != a && (a += ""), a) {
                        if (o.rich) {
                            var l = s.parseRichText(a, o);
                            a = [];
                            for (var h = 0; h < l.lines.length; h++) {
                                for (var c = l.lines[h].tokens, f = [], d = 0; d < c.length; d++) f.push(c[d].text);
                                a.push(f.join(""))
                            }
                            a = a.join("\n")
                        }
                        var p, v, _ = o.textAlign,
                            m = o.textVerticalAlign,
                            x = function (t) {
                                var e = R[t];
                                if (!e) {
                                    E > 100 && (E = 0, R = {});
                                    var r, n = j.style;
                                    try {
                                        n.font = t, r = n.fontFamily.split(",")[0]
                                    } catch (t) {}
                                    e = {
                                        style: n.fontStyle || "normal",
                                        variant: n.fontVariant || "normal",
                                        weight: n.fontWeight || "normal",
                                        size: 0 | parseFloat(n.fontSize || 12),
                                        family: r || "Microsoft YaHei"
                                    }, R[t] = e, E++
                                }
                                return e
                            }(o.font),
                            b = x.style + " " + x.variant + " " + x.weight + " " + x.size + 'px "' + x.family + '"';
                        r = r || s.getBoundingRect(a, b, _, m);
                        var w = this.transform;
                        if (w && !n && (N.copy(e), N.applyTransform(w), e = N), n) p = e.x, v = e.y;
                        else {
                            var S = o.textPosition,
                                k = o.textDistance;
                            if (S instanceof Array) p = e.x + B(S[0], e.width), v = e.y + B(S[1], e.height), _ = _ || "left";
                            else {
                                var T = s.adjustTextPositionOnRect(S, e, k);
                                p = T.x, v = T.y, _ = _ || T.textAlign, m = m || T.textVerticalAlign
                            }
                        }
                        p = s.adjustTextX(p, r.width, _), v = s.adjustTextY(v, r.height, m), v += r.height / 2;
                        var A, P, D, O = g.createNode,
                            I = this._textVmlEl;
                        I ? P = (A = (D = I.firstChild).nextSibling).nextSibling : (I = O("line"), A = O("path"), P = O("textpath"), D = O("skew"), P.style["v-text-align"] = "left", C(I), A.textpathok = !0, P.on = !0, I.from = "0 0", I.to = "1000 0.05", L(I, D), L(I, A), L(I, P), this._textVmlEl = I);
                        var F = [p, v],
                            G = I.style;
                        w && n ? (i(F, F, w), D.on = !0, D.matrix = w[0].toFixed(3) + "," + w[2].toFixed(3) + "," + w[1].toFixed(3) + "," + w[3].toFixed(3) + ",0,0", D.offset = (y(F[0]) || 0) + "," + (y(F[1]) || 0), D.origin = "0 0", G.left = "0px", G.top = "0px") : (D.on = !1, G.left = y(p) + "px", G.top = y(v) + "px"), P.string = String(a).replace(/&/g, "&amp;").replace(/"/g, "&quot;");
                        try {
                            P.style.font = b
                        } catch (t) {}
                        z(I, "fill", {
                            fill: o.textFill,
                            opacity: o.opacity
                        }, this), z(I, "stroke", {
                            stroke: o.textStroke,
                            opacity: o.opacity,
                            lineDash: o.lineDash
                        }, this), I.style.zIndex = M(this.zlevel, this.z, this.z2), L(t, I)
                    }
                }, G = function (t) {
                    P(t, this._textVmlEl), this._textVmlEl = null
                }, W = function (t) {
                    L(t, this._textVmlEl)
                }, H = [l, h, c, d, f], X = 0; X < H.length; X++) {
                    var q = H[X].prototype;
                    q.drawRectText = F, q.removeRectText = G, q.appendRectText = W
                }
                f.prototype.brushVML = function (t) {
                    var e = this.style;
                    null != e.text ? this.drawRectText(t, {
                        x: e.x || 0,
                        y: e.y || 0,
                        width: 0,
                        height: 0
                    }, this.getBoundingRect(), !0) : this.removeRectText(t)
                }, f.prototype.onRemove = function (t) {
                    this.removeRectText(t)
                }, f.prototype.onAdd = function (t) {
                    this.appendRectText(t)
                }
            }
        },
        function (t, e, r) {
            r(46), (0, r(24).registerPainter)("vml", r(45))
        },
        function (t, e, r) {
            var n = r(16);

            function i(t, e) {
                n.call(this, t, e, ["filter"], "__filter_in_use__", "_shadowDom")
            }

            function o(t) {
                return t && (t.shadowBlur || t.shadowOffsetX || t.shadowOffsetY || t.textShadowBlur || t.textShadowOffsetX || t.textShadowOffsetY)
            }
            r(0).inherits(i, n), i.prototype.addWithoutUpdate = function (t, e) {
                if (e && o(e.style)) {
                    var r, n = e.style;
                    if (n._shadowDom) r = n._shadowDom, this.getDefs(!0).contains(n._shadowDom) || this.addDom(r);
                    else r = this.add(e);
                    this.markUsed(e);
                    var i = r.getAttribute("id");
                    t.style.filter = "url(#" + i + ")"
                }
            }, i.prototype.add = function (t) {
                var e = this.createElement("filter"),
                    r = t.style;
                return r._shadowDomId = r._shadowDomId || this.nextId++, e.setAttribute("id", "zr" + this._zrId + "-shadow-" + r._shadowDomId), this.updateDom(t, e), this.addDom(e), e
            }, i.prototype.update = function (t, e) {
                var r = e.style;
                if (o(r)) {
                    var i = this;
                    n.prototype.update.call(this, e, function (t) {
                        i.updateDom(e, t._shadowDom)
                    })
                } else this.remove(t, r)
            }, i.prototype.remove = function (t, e) {
                null != e._shadowDomId && (this.removeDom(e), t.style.filter = "")
            }, i.prototype.updateDom = function (t, e) {
                var r = e.getElementsByTagName("feDropShadow");
                r = 0 === r.length ? this.createElement("feDropShadow") : r[0];
                var n, i, o, a, s = t.style,
                    u = t.scale && t.scale[0] || 1,
                    l = t.scale && t.scale[1] || 1;
                if (s.shadowBlur || s.shadowOffsetX || s.shadowOffsetY) n = s.shadowOffsetX || 0, i = s.shadowOffsetY || 0, o = s.shadowBlur, a = s.shadowColor;
                else {
                    if (!s.textShadowBlur) return void this.removeDom(e, s);
                    n = s.textShadowOffsetX || 0, i = s.textShadowOffsetY || 0, o = s.textShadowBlur, a = s.textShadowColor
                }
                r.setAttribute("dx", n / u), r.setAttribute("dy", i / l), r.setAttribute("flood-color", a);
                var h = o / 2 / u + " " + o / 2 / l;
                r.setAttribute("stdDeviation", h), e.setAttribute("x", "-100%"), e.setAttribute("y", "-100%"), e.setAttribute("width", Math.ceil(o / 2 * 200) + "%"), e.setAttribute("height", Math.ceil(o / 2 * 200) + "%"), e.appendChild(r), s._shadowDom = e
            }, i.prototype.markUsed = function (t) {
                var e = t.style;
                e && e._shadowDom && n.prototype.markUsed.call(this, e._shadowDom)
            };
            var a = i;
            t.exports = a
        },
        function (t, e, r) {
            var n = r(16),
                i = r(0),
                o = r(14);

            function a(t, e) {
                n.call(this, t, e, "clipPath", "__clippath_in_use__")
            }
            i.inherits(a, n), a.prototype.update = function (t) {
                var e = this.getSvgElement(t);
                e && this.updateDom(e, t.__clipPaths, !1);
                var r = this.getTextSvgElement(t);
                r && this.updateDom(r, t.__clipPaths, !0), this.markUsed(t)
            }, a.prototype.updateDom = function (t, e, r) {
                if (e && e.length > 0) {
                    var n, i, a = this.getDefs(!0),
                        s = e[0],
                        u = r ? "_textDom" : "_dom";
                    s[u] ? (i = s[u].getAttribute("id"), n = s[u], a.contains(n) || a.appendChild(n)) : (i = "zr" + this._zrId + "-clip-" + this.nextId, ++this.nextId, (n = this.createElement("clipPath")).setAttribute("id", i), a.appendChild(n), s[u] = n);
                    var l = this.getSvgProxy(s);
                    if (s.transform && s.parent.invTransform && !r) {
                        var h = Array.prototype.slice.call(s.transform);
                        o.mul(s.transform, s.parent.invTransform, s.transform), l.brush(s), s.transform = h
                    } else l.brush(s);
                    var c = this.getSvgElement(s);
                    n.innerHTML = "", n.appendChild(c.cloneNode()), t.setAttribute("clip-path", "url(#" + i + ")"), e.length > 1 && this.updateDom(n, e.slice(1), r)
                } else t && t.setAttribute("clip-path", "none")
            }, a.prototype.markUsed = function (t) {
                var e = this;
                t.__clipPaths && t.__clipPaths.length > 0 && i.each(t.__clipPaths, function (t) {
                    t._dom && n.prototype.markUsed.call(e, t._dom), t._textDom && n.prototype.markUsed.call(e, t._textDom)
                })
            };
            var s = a;
            t.exports = s
        },
        function (t, e, r) {
            var n = r(16),
                i = r(0),
                o = r(10);

            function a(t, e) {
                n.call(this, t, e, ["linearGradient", "radialGradient"], "__gradient_in_use__")
            }
            i.inherits(a, n), a.prototype.addWithoutUpdate = function (t, e) {
                if (e && e.style) {
                    var r = this;
                    i.each(["fill", "stroke"], function (n) {
                        if (e.style[n] && ("linear" === e.style[n].type || "radial" === e.style[n].type)) {
                            var i, o = e.style[n],
                                a = r.getDefs(!0);
                            o._dom ? (i = o._dom, a.contains(o._dom) || r.addDom(i)) : i = r.add(o), r.markUsed(e);
                            var s = i.getAttribute("id");
                            t.setAttribute(n, "url(#" + s + ")")
                        }
                    })
                }
            }, a.prototype.add = function (t) {
                var e;
                if ("linear" === t.type) e = this.createElement("linearGradient");
                else {
                    if ("radial" !== t.type) return o("Illegal gradient type."), null;
                    e = this.createElement("radialGradient")
                }
                return t.id = t.id || this.nextId++, e.setAttribute("id", "zr" + this._zrId + "-gradient-" + t.id), this.updateDom(t, e), this.addDom(e), e
            }, a.prototype.update = function (t) {
                var e = this;
                n.prototype.update.call(this, t, function () {
                    var r = t.type,
                        n = t._dom.tagName;
                    "linear" === r && "linearGradient" === n || "radial" === r && "radialGradient" === n ? e.updateDom(t, t._dom) : (e.removeDom(t), e.add(t))
                })
            }, a.prototype.updateDom = function (t, e) {
                if ("linear" === t.type) e.setAttribute("x1", t.x), e.setAttribute("y1", t.y), e.setAttribute("x2", t.x2), e.setAttribute("y2", t.y2);
                else {
                    if ("radial" !== t.type) return void o("Illegal gradient type.");
                    e.setAttribute("cx", t.x), e.setAttribute("cy", t.y), e.setAttribute("r", t.r)
                }
                t.global ? e.setAttribute("gradientUnits", "userSpaceOnUse") : e.setAttribute("gradientUnits", "objectBoundingBox"), e.innerHTML = "";
                for (var r = t.colorStops, n = 0, i = r.length; n < i; ++n) {
                    var a = this.createElement("stop");
                    a.setAttribute("offset", 100 * r[n].offset + "%"), a.setAttribute("stop-color", r[n].color), e.appendChild(a)
                }
                t._dom = e
            }, a.prototype.markUsed = function (t) {
                if (t.style) {
                    var e = t.style.fill;
                    e && e._dom && n.prototype.markUsed.call(this, e._dom), (e = t.style.stroke) && e._dom && n.prototype.markUsed.call(this, e._dom)
                }
            };
            var s = a;
            t.exports = s
        },
        function (t, e) {
            function r() {}

            function n(t, e, r, n) {
                for (var i = 0, o = e.length, a = 0, s = 0; i < o; i++) {
                    var u = e[i];
                    if (u.removed) {
                        for (l = [], h = s; h < s + u.count; h++) l.push(h);
                        u.indices = l, s += u.count
                    } else {
                        for (var l = [], h = a; h < a + u.count; h++) l.push(h);
                        u.indices = l, a += u.count, u.added || (s += u.count)
                    }
                }
                return e
            }
            r.prototype = {
                diff: function (t, e, r) {
                    r || (r = function (t, e) {
                        return t === e
                    }), this.equals = r;
                    var i = this;
                    t = t.slice();
                    var o = (e = e.slice()).length,
                        a = t.length,
                        s = 1,
                        u = o + a,
                        l = [{
                            newPos: -1,
                            components: []
                        }],
                        h = this.extractCommon(l[0], e, t, 0);
                    if (l[0].newPos + 1 >= o && h + 1 >= a) {
                        for (var c = [], f = 0; f < e.length; f++) c.push(f);
                        return [{
                            indices: c,
                            count: e.length
                        }]
                    }

                    function d() {
                        for (var r = -1 * s; r <= s; r += 2) {
                            var u, h = l[r - 1],
                                c = l[r + 1],
                                f = (c ? c.newPos : 0) - r;
                            h && (l[r - 1] = void 0);
                            var d = h && h.newPos + 1 < o,
                                p = c && 0 <= f && f < a;
                            if (d || p) {
                                if (!d || p && h.newPos < c.newPos ? (u = {
                                    newPos: (v = c).newPos,
                                    components: v.components.slice(0)
                                }, i.pushComponent(u.components, void 0, !0)) : ((u = h).newPos++, i.pushComponent(u.components, !0, void 0)), f = i.extractCommon(u, e, t, r), u.newPos + 1 >= o && f + 1 >= a) return n(i, u.components, e, t);
                                l[r] = u
                            } else l[r] = void 0
                        }
                        var v;
                        s++
                    }
                    for (; s <= u;) {
                        var p = d();
                        if (p) return p
                    }
                }, pushComponent: function (t, e, r) {
                    var n = t[t.length - 1];
                    n && n.added === e && n.removed === r ? t[t.length - 1] = {
                        count: n.count + 1,
                        added: e,
                        removed: r
                    } : t.push({
                        count: 1,
                        added: e,
                        removed: r
                    })
                }, extractCommon: function (t, e, r, n) {
                    for (var i = e.length, o = r.length, a = t.newPos, s = a - n, u = 0; a + 1 < i && s + 1 < o && this.equals(e[a + 1], r[s + 1]);) a++, s++, u++;
                    return u && t.components.push({
                        count: u
                    }), t.newPos = a, s
                }, tokenize: function (t) {
                    return t.slice()
                }, join: function (t) {
                    return t.slice()
                }
            };
            var i = new r;
            t.exports = function (t, e, r) {
                return i.diff(t, e, r)
            }
        },
        function (t, e, r) {
            var n = r(17).createElement,
                i = r(0),
                o = i.each,
                a = r(10),
                s = r(1),
                u = r(9),
                l = r(7),
                h = r(51),
                c = r(50),
                f = r(49),
                d = r(48),
                p = r(18),
                v = p.path,
                g = p.image,
                _ = p.text;

            function y(t) {
                return parseInt(t, 10)
            }

            function m(t, e) {
                return e && t && e.parentNode !== t
            }

            function x(t, e, r) {
                if (m(t, e) && r) {
                    var n = r.nextSibling;
                    n ? t.insertBefore(e, n) : t.appendChild(e)
                }
            }

            function b(t, e) {
                if (m(t, e)) {
                    var r = t.firstChild;
                    r ? t.insertBefore(e, r) : t.appendChild(e)
                }
            }

            function w(t, e) {
                e && t && e.parentNode === t && t.removeChild(e)
            }

            function S(t) {
                return t.__textSvgEl
            }

            function k(t) {
                return t.__svgEl
            }
            var T = function (t, e, r, o) {
                this.root = t, this.storage = e, this._opts = r = i.extend({}, r || {});
                var a = n("svg");
                a.setAttribute("xmlns", "http://www.w3.org/2000/svg"), a.setAttribute("version", "1.1"), a.setAttribute("baseProfile", "full"), a.style.cssText = "user-select:none;position:absolute;left:0;top:0;", this.gradientManager = new c(o, a), this.clipPathManager = new f(o, a), this.shadowManager = new d(o, a);
                var s = document.createElement("div");
                s.style.cssText = "overflow:hidden;position:relative", this._svgRoot = a, this._viewport = s, t.appendChild(s), s.appendChild(a), this.resize(r.width, r.height), this._visibleList = []
            };
            T.prototype = {
                constructor: T,
                getType: function () {
                    return "svg"
                }, getViewportRoot: function () {
                    return this._viewport
                }, getViewportRootOffset: function () {
                    var t = this.getViewportRoot();
                    if (t) return {
                        offsetLeft: t.offsetLeft || 0,
                        offsetTop: t.offsetTop || 0
                    }
                }, refresh: function () {
                    var t = this.storage.getDisplayList(!0);
                    this._paintList(t)
                }, setBackgroundColor: function (t) {
                    this._viewport.style.background = t
                }, _paintList: function (t) {
                    this.gradientManager.markAllUnused(), this.clipPathManager.markAllUnused(), this.shadowManager.markAllUnused();
                    var e, r, n = this._svgRoot,
                        i = this._visibleList,
                        o = t.length,
                        a = [];
                    for (e = 0; e < o; e++) {
                        var c = t[e],
                            f = (r = c) instanceof s ? v : r instanceof u ? g : r instanceof l ? _ : v,
                            d = k(c) || S(c);
                        c.invisible || (c.__dirty && (f && f.brush(c), this.clipPathManager.update(c), c.style && (this.gradientManager.update(c.style.fill), this.gradientManager.update(c.style.stroke), this.shadowManager.update(d, c)), c.__dirty = !1), a.push(c))
                    }
                    var p, y = h(i, a);
                    for (e = 0; e < y.length; e++) {
                        if ((C = y[e]).removed)
                            for (var m = 0; m < C.count; m++) {
                                d = k(c = i[C.indices[m]]);
                                var T = S(c);
                                w(n, d), w(n, T)
                            }
                    }
                    for (e = 0; e < y.length; e++) {
                        var C;
                        if ((C = y[e]).added)
                            for (m = 0; m < C.count; m++) {
                                d = k(c = a[C.indices[m]]), T = S(c);
                                p ? x(n, d, p) : b(n, d), d ? x(n, T, d) : p ? x(n, T, p) : b(n, T), x(n, T, d), p = T || d || p, this.gradientManager.addWithoutUpdate(d, c), this.shadowManager.addWithoutUpdate(p, c), this.clipPathManager.markUsed(c)
                            } else if (!C.removed)
                                for (m = 0; m < C.count; m++) {
                                    p = d = S(c = a[C.indices[m]]) || k(c) || p, this.gradientManager.markUsed(c), this.gradientManager.addWithoutUpdate(d, c), this.shadowManager.markUsed(c), this.shadowManager.addWithoutUpdate(d, c), this.clipPathManager.markUsed(c)
                                }
                    }
                    this.gradientManager.removeUnused(), this.clipPathManager.removeUnused(), this.shadowManager.removeUnused(), this._visibleList = a
                }, _getDefs: function (t) {
                    var e, r = this._svgRoot;
                    return 0 === (e = this._svgRoot.getElementsByTagName("defs")).length ? t ? ((e = r.insertBefore(n("defs"), r.firstChild)).contains || (e.contains = function (t) {
                        var r = e.children;
                        if (!r) return !1;
                        for (var n = r.length - 1; n >= 0; --n)
                            if (r[n] === t) return !0;
                        return !1
                    }), e) : null : e[0]
                }, resize: function (t, e) {
                    var r = this._viewport;
                    r.style.display = "none";
                    var n = this._opts;
                    if (null != t && (n.width = t), null != e && (n.height = e), t = this._getSize(0), e = this._getSize(1), r.style.display = "", this._width !== t || this._height !== e) {
                        this._width = t, this._height = e;
                        var i = r.style;
                        i.width = t + "px", i.height = e + "px";
                        var o = this._svgRoot;
                        o.setAttribute("width", t), o.setAttribute("height", e)
                    }
                }, getWidth: function () {
                    return this._width
                }, getHeight: function () {
                    return this._height
                }, _getSize: function (t) {
                    var e = this._opts,
                        r = ["width", "height"][t],
                        n = ["clientWidth", "clientHeight"][t],
                        i = ["paddingLeft", "paddingTop"][t],
                        o = ["paddingRight", "paddingBottom"][t];
                    if (null != e[r] && "auto" !== e[r]) return parseFloat(e[r]);
                    var a = this.root,
                        s = document.defaultView.getComputedStyle(a);
                    return (a[n] || y(s[r]) || y(a.style[r])) - (y(s[i]) || 0) - (y(s[o]) || 0) | 0
                }, dispose: function () {
                    this.root.innerHTML = "", this._svgRoot = this._viewport = this.storage = null
                }, clear: function () {
                    this._viewport && this.root.removeChild(this._viewport)
                }, pathToDataUrl: function () {
                    return this.refresh(), "data:image/svg+xml;charset=UTF-8," + this._svgRoot.outerHTML
                }
            }, o(["getLayer", "insertLayer", "eachLayer", "eachBuiltinLayer", "eachOtherLayer", "getLayers", "modLayer", "delLayer", "clearLayer", "toDataURL", "pathToImage"], function (t) {
                var e;
                T.prototype[t] = (e = t, function () {
                    a('In SVG mode painter not support method "' + e + '"')
                })
            });
            var C = T;
            t.exports = C
        },
        function (t, e, r) {
            r(18), (0, r(24).registerPainter)("svg", r(52))
        },
        function (t, e, r) {
            var n = r(0),
                i = r(19),
                o = function (t, e, r, n, o) {
                    this.x = null == t ? .5 : t, this.y = null == e ? .5 : e, this.r = null == r ? .5 : r, this.type = "radial", this.global = o || !1, i.call(this, n)
                };
            o.prototype = {
                constructor: o
            }, n.inherits(o, i);
            var a = o;
            t.exports = a
        },
        function (t, e, r) {
            var n = r(0),
                i = r(19),
                o = function (t, e, r, n, o, a) {
                    this.x = null == t ? 0 : t, this.y = null == e ? 0 : e, this.x2 = null == r ? 1 : r, this.y2 = null == n ? 0 : n, this.type = "linear", this.global = a || !1, i.call(this, o)
                };
            o.prototype = {
                constructor: o
            }, n.inherits(o, i);
            var a = o;
            t.exports = a
        },
        function (t, e, r) {
            var n = r(1),
                i = Math.cos,
                o = Math.sin,
                a = n.extend({
                    type: "trochoid",
                    shape: {
                        cx: 0,
                        cy: 0,
                        r: 0,
                        r0: 0,
                        d: 0,
                        location: "out"
                    },
                    style: {
                        stroke: "#000",
                        fill: null
                    },
                    buildPath: function (t, e) {
                        var r, n, a, s, u = e.r,
                            l = e.r0,
                            h = e.d,
                            c = e.cx,
                            f = e.cy,
                            d = "out" == e.location ? 1 : -1;
                        if (!(e.location && u <= l)) {
                            var p, v = 0,
                                g = 1;
                            r = (u + d * l) * i(0) - d * h * i(0) + c, n = (u + d * l) * o(0) - h * o(0) + f, t.moveTo(r, n);
                            do {
                                v++
                            } while (l * v % (u + d * l) != 0);
                            do {
                                p = Math.PI / 180 * g, a = (u + d * l) * i(p) - d * h * i((u / l + d) * p) + c, s = (u + d * l) * o(p) - h * o((u / l + d) * p) + f, t.lineTo(a, s), g++
                            } while (g <= l * v / (u + d * l) * 360)
                        }
                    }
                });
            t.exports = a
        },
        function (t, e, r) {
            var n = r(1),
                i = Math.PI,
                o = Math.cos,
                a = Math.sin,
                s = n.extend({
                    type: "star",
                    shape: {
                        cx: 0,
                        cy: 0,
                        n: 3,
                        r0: null,
                        r: 0
                    },
                    buildPath: function (t, e) {
                        var r = e.n;
                        if (r && !(r < 2)) {
                            var n = e.cx,
                                s = e.cy,
                                u = e.r,
                                l = e.r0;
                            null == l && (l = r > 4 ? u * o(2 * i / r) / o(i / r) : u / 3);
                            var h = i / r,
                                c = -i / 2,
                                f = n + u * o(c),
                                d = s + u * a(c);
                            c += h, t.moveTo(f, d);
                            for (var p, v = 0, g = 2 * r - 1; v < g; v++) p = v % 2 == 0 ? l : u, t.lineTo(n + p * o(c), s + p * a(c)), c += h;
                            t.closePath()
                        }
                    }
                });
            t.exports = s
        },
        function (t, e, r) {
            var n = r(4),
                i = [
                    ["shadowBlur", 0],
                    ["shadowColor", "#000"],
                    ["shadowOffsetX", 0],
                    ["shadowOffsetY", 0]
                ];
            t.exports = function (t) {
                return n.browser.ie && n.browser.version >= 11 ? function () {
                    var e, r = this.__clipPaths,
                        n = this.style;
                    if (r)
                        for (var o = 0; o < r.length; o++) {
                            var a = r[o],
                                s = a && a.shape,
                                u = a && a.type;
                            if (s && ("sector" === u && s.startAngle === s.endAngle || "rect" === u && (!s.width || !s.height))) {
                                for (var l = 0; l < i.length; l++) i[l][2] = n[i[l][0]], n[i[l][0]] = i[l][1];
                                e = !0;
                                break
                            }
                        }
                    if (t.apply(this, arguments), e)
                        for (l = 0; l < i.length; l++) n[i[l][0]] = i[l][2]
                } : t
            }
        },
        function (t, e, r) {
            var n = r(1),
                i = r(58),
                o = n.extend({
                    type: "sector",
                    shape: {
                        cx: 0,
                        cy: 0,
                        r0: 0,
                        r: 0,
                        startAngle: 0,
                        endAngle: 2 * Math.PI,
                        clockwise: !0
                    },
                    brush: i(n.prototype.brush),
                    buildPath: function (t, e) {
                        var r = e.cx,
                            n = e.cy,
                            i = Math.max(e.r0 || 0, 0),
                            o = Math.max(e.r, 0),
                            a = e.startAngle,
                            s = e.endAngle,
                            u = e.clockwise,
                            l = Math.cos(a),
                            h = Math.sin(a);
                        t.moveTo(l * i + r, h * i + n), t.lineTo(l * o + r, h * o + n), t.arc(r, n, o, a, s, !u), t.lineTo(Math.cos(s) * i + r, Math.sin(s) * i + n), 0 !== i && t.arc(r, n, i, s, a, u), t.closePath()
                    }
                });
            t.exports = o
        },
        function (t, e, r) {
            var n = r(1),
                i = Math.sin,
                o = Math.cos,
                a = Math.PI / 180,
                s = n.extend({
                    type: "rose",
                    shape: {
                        cx: 0,
                        cy: 0,
                        r: [],
                        k: 0,
                        n: 1
                    },
                    style: {
                        stroke: "#000",
                        fill: null
                    },
                    buildPath: function (t, e) {
                        var r, n, s, u = e.r,
                            l = e.k,
                            h = e.n,
                            c = e.cx,
                            f = e.cy;
                        t.moveTo(c, f);
                        for (var d = 0, p = u.length; d < p; d++) {
                            s = u[d];
                            for (var v = 0; v <= 360 * h; v++) r = s * i(l / h * v % 360 * a) * o(v * a) + c, n = s * i(l / h * v % 360 * a) * i(v * a) + f, t.lineTo(r, n)
                        }
                    }
                });
            t.exports = s
        },
        function (t, e, r) {
            var n = r(1).extend({
                type: "ring",
                shape: {
                    cx: 0,
                    cy: 0,
                    r: 0,
                    r0: 0
                },
                buildPath: function (t, e) {
                    var r = e.cx,
                        n = e.cy,
                        i = 2 * Math.PI;
                    t.moveTo(r + e.r, n), t.arc(r, n, e.r, 0, i, !1), t.moveTo(r + e.r0, n), t.arc(r, n, e.r0, 0, i, !0)
                }
            });
            t.exports = n
        },
        function (t, e, r) {
            var n = r(1),
                i = r(28),
                o = n.extend({
                    type: "rect",
                    shape: {
                        r: 0,
                        x: 0,
                        y: 0,
                        width: 0,
                        height: 0
                    },
                    buildPath: function (t, e) {
                        var r = e.x,
                            n = e.y,
                            o = e.width,
                            a = e.height;
                        e.r ? i.buildPath(t, e) : t.rect(r, n, o, a), t.closePath()
                    }
                });
            t.exports = o
        },
        function (t, e, r) {
            var n = r(1),
                i = r(26),
                o = n.extend({
                    type: "polyline",
                    shape: {
                        points: null,
                        smooth: !1,
                        smoothConstraint: null
                    },
                    style: {
                        stroke: "#000",
                        fill: null
                    },
                    buildPath: function (t, e) {
                        i.buildPath(t, e, !1)
                    }
                });
            t.exports = o
        },
        function (t, e, r) {
            var n = r(2),
                i = n.min,
                o = n.max,
                a = n.scale,
                s = n.distance,
                u = n.add,
                l = n.clone,
                h = n.sub;
            t.exports = function (t, e, r, n) {
                var c, f, d, p, v = [],
                    g = [],
                    _ = [],
                    y = [];
                if (n) {
                    d = [1 / 0, 1 / 0], p = [-1 / 0, -1 / 0];
                    for (var m = 0, x = t.length; m < x; m++) i(d, d, t[m]), o(p, p, t[m]);
                    i(d, d, n[0]), o(p, p, n[1])
                }
                for (m = 0, x = t.length; m < x; m++) {
                    var b = t[m];
                    if (r) c = t[m ? m - 1 : x - 1], f = t[(m + 1) % x];
                    else {
                        if (0 === m || m === x - 1) {
                            v.push(l(t[m]));
                            continue
                        }
                        c = t[m - 1], f = t[m + 1]
                    }
                    h(g, f, c), a(g, g, e);
                    var w = s(b, c),
                        S = s(b, f),
                        k = w + S;
                    0 !== k && (w /= k, S /= k), a(_, g, -w), a(y, g, S);
                    var T = u([], b, _),
                        C = u([], b, y);
                    n && (o(T, T, d), i(T, T, p), o(C, C, d), i(C, C, p)), v.push(T), v.push(C)
                }
                return r && v.push(v.shift()), v
            }
        },
        function (t, e, r) {
            var n = r(2).distance;

            function i(t, e, r, n, i, o, a) {
                var s = .5 * (r - t),
                    u = .5 * (n - e);
                return (2 * (e - r) + s + u) * a + (-3 * (e - r) - 2 * s - u) * o + s * i + e
            }
            t.exports = function (t, e) {
                for (var r = t.length, o = [], a = 0, s = 1; s < r; s++) a += n(t[s - 1], t[s]);
                var u = a / 2;
                for (u = u < r ? r : u, s = 0; s < u; s++) {
                    var l, h, c, f = s / (u - 1) * (e ? r : r - 1),
                        d = Math.floor(f),
                        p = f - d,
                        v = t[d % r];
                    e ? (l = t[(d - 1 + r) % r], h = t[(d + 1) % r], c = t[(d + 2) % r]) : (l = t[0 === d ? d : d - 1], h = t[d > r - 2 ? r - 1 : d + 1], c = t[d > r - 3 ? r - 1 : d + 2]);
                    var g = p * p,
                        _ = p * g;
                    o.push([i(l[0], v[0], h[0], c[0], p, g, _), i(l[1], v[1], h[1], c[1], p, g, _)])
                }
                return o
            }
        },
        function (t, e, r) {
            var n = r(1),
                i = r(26),
                o = n.extend({
                    type: "polygon",
                    shape: {
                        points: null,
                        smooth: !1,
                        smoothConstraint: null
                    },
                    buildPath: function (t, e) {
                        i.buildPath(t, e, !0)
                    }
                });
            t.exports = o
        },
        function (t, e, r) {
            var n = r(1).extend({
                type: "line",
                shape: {
                    x1: 0,
                    y1: 0,
                    x2: 0,
                    y2: 0,
                    percent: 1
                },
                style: {
                    stroke: "#000",
                    fill: null
                },
                buildPath: function (t, e) {
                    var r = e.x1,
                        n = e.y1,
                        i = e.x2,
                        o = e.y2,
                        a = e.percent;
                    0 !== a && (t.moveTo(r, n), a < 1 && (i = r * (1 - a) + i * a, o = n * (1 - a) + o * a), t.lineTo(i, o))
                }, pointAt: function (t) {
                    var e = this.shape;
                    return [e.x1 * (1 - t) + e.x2 * t, e.y1 * (1 - t) + e.y2 * t]
                }
            });
            t.exports = n
        },
        function (t, e, r) {
            var n = r(1),
                i = Math.PI,
                o = Math.sin,
                a = Math.cos,
                s = n.extend({
                    type: "isogon",
                    shape: {
                        x: 0,
                        y: 0,
                        r: 0,
                        n: 0
                    },
                    buildPath: function (t, e) {
                        var r = e.n;
                        if (r && !(r < 2)) {
                            var n = e.x,
                                s = e.y,
                                u = e.r,
                                l = 2 * i / r,
                                h = -i / 2;
                            t.moveTo(n + u * a(h), s + u * o(h));
                            for (var c = 0, f = r - 1; c < f; c++) h += l, t.lineTo(n + u * a(h), s + u * o(h));
                            t.closePath()
                        }
                    }
                });
            t.exports = s
        },
        function (t, e, r) {
            var n = r(1).extend({
                type: "heart",
                shape: {
                    cx: 0,
                    cy: 0,
                    width: 0,
                    height: 0
                },
                buildPath: function (t, e) {
                    var r = e.cx,
                        n = e.cy,
                        i = e.width,
                        o = e.height;
                    t.moveTo(r, n), t.bezierCurveTo(r + i / 2, n - 2 * o / 3, r + 2 * i, n + o / 3, r, n + o), t.bezierCurveTo(r - 2 * i, n + o / 3, r - i / 2, n - 2 * o / 3, r, n)
                }
            });
            t.exports = n
        },
        function (t, e, r) {
            var n = r(1).extend({
                type: "ellipse",
                shape: {
                    cx: 0,
                    cy: 0,
                    rx: 0,
                    ry: 0
                },
                buildPath: function (t, e) {
                    var r = .5522848,
                        n = e.cx,
                        i = e.cy,
                        o = e.rx,
                        a = e.ry,
                        s = o * r,
                        u = a * r;
                    t.moveTo(n - o, i), t.bezierCurveTo(n - o, i - u, n - s, i - a, n, i - a), t.bezierCurveTo(n + s, i - a, n + o, i - u, n + o, i), t.bezierCurveTo(n + o, i + u, n + s, i + a, n, i + a), t.bezierCurveTo(n - s, i + a, n - o, i + u, n - o, i), t.closePath()
                }
            });
            t.exports = n
        },
        function (t, e, r) {
            var n = r(1).extend({
                type: "droplet",
                shape: {
                    cx: 0,
                    cy: 0,
                    width: 0,
                    height: 0
                },
                buildPath: function (t, e) {
                    var r = e.cx,
                        n = e.cy,
                        i = e.width,
                        o = e.height;
                    t.moveTo(r, n + i), t.bezierCurveTo(r + i, n + i, r + 3 * i / 2, n - i / 3, r, n - o), t.bezierCurveTo(r - 3 * i / 2, n - i / 3, r - i, n + i, r, n + i), t.closePath()
                }
            });
            t.exports = n
        },
        function (t, e, r) {
            var n = r(1).extend({
                type: "circle",
                shape: {
                    cx: 0,
                    cy: 0,
                    r: 0
                },
                buildPath: function (t, e, r) {
                    r && t.moveTo(e.cx + e.r, e.cy), t.arc(e.cx, e.cy, e.r, 0, 2 * Math.PI, !0)
                }
            });
            t.exports = n
        },
        function (t, e, r) {
            var n = r(1),
                i = r(2),
                o = r(5),
                a = o.quadraticSubdivide,
                s = o.cubicSubdivide,
                u = o.quadraticAt,
                l = o.cubicAt,
                h = o.quadraticDerivativeAt,
                c = o.cubicDerivativeAt,
                f = [];

            function d(t, e, r) {
                var n = t.cpx2,
                    i = t.cpy2;
                return null === n || null === i ? [(r ? c : l)(t.x1, t.cpx1, t.cpx2, t.x2, e), (r ? c : l)(t.y1, t.cpy1, t.cpy2, t.y2, e)] : [(r ? h : u)(t.x1, t.cpx1, t.x2, e), (r ? h : u)(t.y1, t.cpy1, t.y2, e)]
            }
            var p = n.extend({
                type: "bezier-curve",
                shape: {
                    x1: 0,
                    y1: 0,
                    x2: 0,
                    y2: 0,
                    cpx1: 0,
                    cpy1: 0,
                    percent: 1
                },
                style: {
                    stroke: "#000",
                    fill: null
                },
                buildPath: function (t, e) {
                    var r = e.x1,
                        n = e.y1,
                        i = e.x2,
                        o = e.y2,
                        u = e.cpx1,
                        l = e.cpy1,
                        h = e.cpx2,
                        c = e.cpy2,
                        d = e.percent;
                    0 !== d && (t.moveTo(r, n), null == h || null == c ? (d < 1 && (a(r, u, i, d, f), u = f[1], i = f[2], a(n, l, o, d, f), l = f[1], o = f[2]), t.quadraticCurveTo(u, l, i, o)) : (d < 1 && (s(r, u, h, i, d, f), u = f[1], h = f[2], i = f[3], s(n, l, c, o, d, f), l = f[1], c = f[2], o = f[3]), t.bezierCurveTo(u, l, h, c, i, o)))
                }, pointAt: function (t) {
                    return d(this.shape, t, !1)
                }, tangentAt: function (t) {
                    var e = d(this.shape, t, !0);
                    return i.normalize(e, e)
                }
            });
            t.exports = p
        },
        function (t, e, r) {
            var n = r(1).extend({
                type: "arc",
                shape: {
                    cx: 0,
                    cy: 0,
                    r: 0,
                    startAngle: 0,
                    endAngle: 2 * Math.PI,
                    clockwise: !0
                },
                style: {
                    stroke: "#000",
                    fill: null
                },
                buildPath: function (t, e) {
                    var r = e.cx,
                        n = e.cy,
                        i = Math.max(e.r, 0),
                        o = e.startAngle,
                        a = e.endAngle,
                        s = e.clockwise,
                        u = Math.cos(o),
                        l = Math.sin(o);
                    t.moveTo(u * i + r, l * i + n), t.arc(r, n, i, o, a, !s)
                }
            });
            t.exports = n
        },
        function (t, e, r) {
            var n = r(0).inherits,
                i = r(8),
                o = r(3);

            function a(t) {
                i.call(this, t), this._displayables = [], this._temporaryDisplayables = [], this._cursor = 0, this.notClear = !0
            }
            a.prototype.incremental = !0, a.prototype.clearDisplaybles = function () {
                this._displayables = [], this._temporaryDisplayables = [], this._cursor = 0, this.dirty(), this.notClear = !1
            }, a.prototype.addDisplayable = function (t, e) {
                e ? this._temporaryDisplayables.push(t) : this._displayables.push(t), this.dirty()
            }, a.prototype.addDisplayables = function (t, e) {
                e = e || !1;
                for (var r = 0; r < t.length; r++) this.addDisplayable(t[r], e)
            }, a.prototype.eachPendingDisplayable = function (t) {
                for (var e = this._cursor; e < this._displayables.length; e++) t && t(this._displayables[e]);
                for (e = 0; e < this._temporaryDisplayables.length; e++) t && t(this._temporaryDisplayables[e])
            }, a.prototype.update = function () {
                this.updateTransform();
                for (var t = this._cursor; t < this._displayables.length; t++) {
                    (e = this._displayables[t]).parent = this, e.update(), e.parent = null
                }
                for (t = 0; t < this._temporaryDisplayables.length; t++) {
                    var e;
                    (e = this._temporaryDisplayables[t]).parent = this, e.update(), e.parent = null
                }
            }, a.prototype.brush = function (t, e) {
                for (var r = this._cursor; r < this._displayables.length; r++) {
                    (n = this._temporaryDisplayables[r]).beforeBrush && n.beforeBrush(t), n.brush(t, r === this._cursor ? null : this._displayables[r - 1]), n.afterBrush && n.afterBrush(t)
                }
                this._cursor = r;
                for (r = 0; r < this._temporaryDisplayables.length; r++) {
                    var n;
                    (n = this._temporaryDisplayables[r]).beforeBrush && n.beforeBrush(t), n.brush(t, 0 === r ? null : this._temporaryDisplayables[r - 1]), n.afterBrush && n.afterBrush(t)
                }
                this._temporaryDisplayables = [], this.notClear = !0
            };
            var s = [];
            a.prototype.getBoundingRect = function () {
                if (!this._rect) {
                    for (var t = new o(1 / 0, 1 / 0, -1 / 0, -1 / 0), e = 0; e < this._displayables.length; e++) {
                        var r = this._displayables[e],
                            n = r.getBoundingRect().clone();
                        r.needLocalTransform() && n.applyTransform(r.getLocalTransform(s)), t.union(n)
                    }
                    this._rect = t
                }
                return this._rect
            }, a.prototype.contain = function (t, e) {
                var r = this.transformCoordToLocal(t, e);
                if (this.getBoundingRect().contain(r[0], r[1]))
                    for (var n = 0; n < this._displayables.length; n++) {
                        if (this._displayables[n].contain(t, e)) return !0
                    }
                return !1
            }, n(a, i);
            var u = a;
            t.exports = u
        },
        function (t, e, r) {
            var n = r(1),
                i = n.extend({
                    type: "compound",
                    shape: {
                        paths: null
                    },
                    _updatePathDirty: function () {
                        for (var t = this.__dirtyPath, e = this.shape.paths, r = 0; r < e.length; r++) t = t || e[r].__dirtyPath;
                        this.__dirtyPath = t, this.__dirty = this.__dirty || t
                    }, beforeBrush: function () {
                        this._updatePathDirty();
                        for (var t = this.shape.paths || [], e = this.getGlobalScale(), r = 0; r < t.length; r++) t[r].path || t[r].createPathProxy(), t[r].path.setScale(e[0], e[1])
                    }, buildPath: function (t, e) {
                        for (var r = e.paths || [], n = 0; n < r.length; n++) r[n].buildPath(t, r[n].shape, !0)
                    }, afterBrush: function () {
                        for (var t = this.shape.paths || [], e = 0; e < t.length; e++) t[e].__dirtyPath = !1
                    }, getBoundingRect: function () {
                        return this._updatePathDirty(), n.prototype.getBoundingRect.call(this)
                    }
                });
            t.exports = i
        },
        function (t, e, r) {
            var n = r(6),
                i = r(2).applyTransform,
                o = n.CMD,
                a = [
                    [],
                    [],
                    []
                ],
                s = Math.sqrt,
                u = Math.atan2;
            t.exports = function (t, e) {
                var r, n, l, h, c, f = t.data,
                    d = o.M,
                    p = o.C,
                    v = o.L,
                    g = o.R,
                    _ = o.A,
                    y = o.Q;
                for (l = 0, h = 0; l < f.length;) {
                    switch (r = f[l++], h = l, n = 0, r) {
                    case d:
                    case v:
                        n = 1;
                        break;
                    case p:
                        n = 3;
                        break;
                    case y:
                        n = 2;
                        break;
                    case _:
                        var m = e[4],
                            x = e[5],
                            b = s(e[0] * e[0] + e[1] * e[1]),
                            w = s(e[2] * e[2] + e[3] * e[3]),
                            S = u(-e[1] / w, e[0] / b);
                        f[l] *= b, f[l++] += m, f[l] *= w, f[l++] += x, f[l++] *= b, f[l++] *= w, f[l++] += S, f[l++] += S, h = l += 2;
                        break;
                    case g:
                        k[0] = f[l++], k[1] = f[l++], i(k, k, e), f[h++] = k[0], f[h++] = k[1], k[0] += f[l++], k[1] += f[l++], i(k, k, e), f[h++] = k[0], f[h++] = k[1]
                    }
                    for (c = 0; c < n; c++) {
                        var k;
                        (k = a[c])[0] = f[l++], k[1] = f[l++], i(k, k, e), f[h++] = k[0], f[h++] = k[1]
                    }
                }
            }
        },
        function (t, e) {
            t.exports = function (t, e, r, n, i, o) {
                if (o > e && o > n || o < e && o < n) return 0;
                if (n === e) return 0;
                var a = n < e ? 1 : -1,
                    s = (o - e) / (n - e);
                return 1 !== s && 0 !== s || (a = n < e ? .5 : -.5), s * (r - t) + t > i ? a : 0
            }
        },
        function (t, e, r) {
            var n = r(27).normalizeRadian,
                i = 2 * Math.PI;
            e.containStroke = function (t, e, r, o, a, s, u, l, h) {
                if (0 === u) return !1;
                var c = u;
                l -= t, h -= e;
                var f = Math.sqrt(l * l + h * h);
                if (f - c > r || f + c < r) return !1;
                if (Math.abs(o - a) % i < 1e-4) return !0;
                if (s) {
                    var d = o;
                    o = n(a), a = n(d)
                } else o = n(o), a = n(a);
                o > a && (a += i);
                var p = Math.atan2(h, l);
                return p < 0 && (p += i), p >= o && p <= a || p + i >= o && p + i <= a
            }
        },
        function (t, e, r) {
            var n = r(5).quadraticProjectPoint;
            e.containStroke = function (t, e, r, i, o, a, s, u, l) {
                if (0 === s) return !1;
                var h = s;
                return !(l > e + h && l > i + h && l > a + h || l < e - h && l < i - h && l < a - h || u > t + h && u > r + h && u > o + h || u < t - h && u < r - h && u < o - h) && n(t, e, r, i, o, a, u, l, null) <= h / 2
            }
        },
        function (t, e, r) {
            var n = r(5);
            e.containStroke = function (t, e, r, i, o, a, s, u, l, h, c) {
                if (0 === l) return !1;
                var f = l;
                return !(c > e + f && c > i + f && c > a + f && c > u + f || c < e - f && c < i - f && c < a - f && c < u - f || h > t + f && h > r + f && h > o + f && h > s + f || h < t - f && h < r - f && h < o - f && h < s - f) && n.cubicProjectPoint(t, e, r, i, o, a, s, u, h, c, null) <= f / 2
            }
        },
        function (t, e) {
            e.containStroke = function (t, e, r, n, i, o, a) {
                if (0 === i) return !1;
                var s = i,
                    u = 0;
                if (a > e + s && a > n + s || a < e - s && a < n - s || o > t + s && o > r + s || o < t - s && o < r - s) return !1;
                if (t === r) return Math.abs(o - t) <= s / 2;
                var l = (u = (e - n) / (t - r)) * o - a + (t * n - r * e) / (t - r);
                return l * l / (u * u + 1) <= s / 2 * s / 2
            }
        },
        function (t, e, r) {
            var n = r(6),
                i = r(82),
                o = r(81),
                a = r(80),
                s = r(79),
                u = r(27).normalizeRadian,
                l = r(5),
                h = r(78),
                c = n.CMD,
                f = 2 * Math.PI,
                d = 1e-4;
            var p = [-1, -1, -1],
                v = [-1, -1];

            function g(t, e, r, n, i, o, a, s, u, h) {
                if (h > e && h > n && h > o && h > s || h < e && h < n && h < o && h < s) return 0;
                var c, f = l.cubicRootAt(e, n, o, s, h, p);
                if (0 === f) return 0;
                for (var d, g, _ = 0, y = -1, m = 0; m < f; m++) {
                    var x = p[m],
                        b = 0 === x || 1 === x ? .5 : 1;
                    l.cubicAt(t, r, i, a, x) < u || (y < 0 && (y = l.cubicExtrema(e, n, o, s, v), v[1] < v[0] && y > 1 && (void 0, c = v[0], v[0] = v[1], v[1] = c), d = l.cubicAt(e, n, o, s, v[0]), y > 1 && (g = l.cubicAt(e, n, o, s, v[1]))), 2 == y ? x < v[0] ? _ += d < e ? b : -b : x < v[1] ? _ += g < d ? b : -b : _ += s < g ? b : -b : x < v[0] ? _ += d < e ? b : -b : _ += s < d ? b : -b)
                }
                return _
            }

            function _(t, e, r, n, i, o, a, s) {
                if (s > e && s > n && s > o || s < e && s < n && s < o) return 0;
                var u = l.quadraticRootAt(e, n, o, s, p);
                if (0 === u) return 0;
                var h = l.quadraticExtremum(e, n, o);
                if (h >= 0 && h <= 1) {
                    for (var c = 0, f = l.quadraticAt(e, n, o, h), d = 0; d < u; d++) {
                        var v = 0 === p[d] || 1 === p[d] ? .5 : 1;
                        l.quadraticAt(t, r, i, p[d]) < a || (p[d] < h ? c += f < e ? v : -v : c += o < f ? v : -v)
                    }
                    return c
                }
                v = 0 === p[0] || 1 === p[0] ? .5 : 1;
                return l.quadraticAt(t, r, i, p[0]) < a ? 0 : o < e ? v : -v
            }

            function y(t, e, r, n, i, o, a, s) {
                if ((s -= e) > r || s < -r) return 0;
                var l = Math.sqrt(r * r - s * s);
                p[0] = -l, p[1] = l;
                var h = Math.abs(n - i);
                if (h < 1e-4) return 0;
                if (h % f < 1e-4) {
                    n = 0, i = f;
                    var c = o ? 1 : -1;
                    return a >= p[0] + t && a <= p[1] + t ? c : 0
                }
                if (o) {
                    l = n;
                    n = u(i), i = u(l)
                } else n = u(n), i = u(i);
                n > i && (i += f);
                for (var d = 0, v = 0; v < 2; v++) {
                    var g = p[v];
                    if (g + t > a) {
                        var _ = Math.atan2(s, g);
                        c = o ? 1 : -1;
                        _ < 0 && (_ = f + _), (_ >= n && _ <= i || _ + f >= n && _ + f <= i) && (_ > Math.PI / 2 && _ < 1.5 * Math.PI && (c = -c), d += c)
                    }
                }
                return d
            }

            function m(t, e, r, n, u) {
                for (var l, f, p = 0, v = 0, m = 0, x = 0, b = 0, w = 0; w < t.length;) {
                    var S = t[w++];
                    switch (S === c.M && w > 1 && (r || (p += h(v, m, x, b, n, u))), 1 == w && (x = v = t[w], b = m = t[w + 1]), S) {
                    case c.M:
                        v = x = t[w++], m = b = t[w++];
                        break;
                    case c.L:
                        if (r) {
                            if (i.containStroke(v, m, t[w], t[w + 1], e, n, u)) return !0
                        } else p += h(v, m, t[w], t[w + 1], n, u) || 0;
                        v = t[w++], m = t[w++];
                        break;
                    case c.C:
                        if (r) {
                            if (o.containStroke(v, m, t[w++], t[w++], t[w++], t[w++], t[w], t[w + 1], e, n, u)) return !0
                        } else p += g(v, m, t[w++], t[w++], t[w++], t[w++], t[w], t[w + 1], n, u) || 0;
                        v = t[w++], m = t[w++];
                        break;
                    case c.Q:
                        if (r) {
                            if (a.containStroke(v, m, t[w++], t[w++], t[w], t[w + 1], e, n, u)) return !0
                        } else p += _(v, m, t[w++], t[w++], t[w], t[w + 1], n, u) || 0;
                        v = t[w++], m = t[w++];
                        break;
                    case c.A:
                        var k = t[w++],
                            T = t[w++],
                            C = t[w++],
                            A = t[w++],
                            L = t[w++],
                            P = t[w++],
                            M = (t[w++], 1 - t[w++]),
                            B = Math.cos(L) * C + k,
                            D = Math.sin(L) * A + T;
                        w > 1 ? p += h(v, m, B, D, n, u) : (x = B, b = D);
                        var z = (n - k) * A / C + k;
                        if (r) {
                            if (s.containStroke(k, T, A, L, L + P, M, e, z, u)) return !0
                        } else p += y(k, T, A, L, L + P, M, z, u);
                        v = Math.cos(L + P) * C + k, m = Math.sin(L + P) * A + T;
                        break;
                    case c.R:
                        x = v = t[w++], b = m = t[w++];
                        B = x + t[w++], D = b + t[w++];
                        if (r) {
                            if (i.containStroke(x, b, B, b, e, n, u) || i.containStroke(B, b, B, D, e, n, u) || i.containStroke(B, D, x, D, e, n, u) || i.containStroke(x, D, x, b, e, n, u)) return !0
                        } else p += h(B, b, B, D, n, u), p += h(x, D, x, b, n, u);
                        break;
                    case c.Z:
                        if (r) {
                            if (i.containStroke(v, m, x, b, e, n, u)) return !0
                        } else p += h(v, m, x, b, n, u);
                        v = x, m = b
                    }
                }
                return r || (l = m, f = b, Math.abs(l - f) < d) || (p += h(v, m, x, b, n, u) || 0), 0 !== p
            }
            e.contain = function (t, e, r) {
                return m(t, 0, !1, e, r)
            }, e.containStroke = function (t, e, r, n) {
                return m(t, e, !0, r, n)
            }
        },
        function (t, e, r) {
            var n = r(2),
                i = r(5),
                o = Math.min,
                a = Math.max,
                s = Math.sin,
                u = Math.cos,
                l = 2 * Math.PI,
                h = n.create(),
                c = n.create(),
                f = n.create();
            var d = [],
                p = [];
            e.fromPoints = function (t, e, r) {
                if (0 !== t.length) {
                    var n, i = t[0],
                        s = i[0],
                        u = i[0],
                        l = i[1],
                        h = i[1];
                    for (n = 1; n < t.length; n++) i = t[n], s = o(s, i[0]), u = a(u, i[0]), l = o(l, i[1]), h = a(h, i[1]);
                    e[0] = s, e[1] = l, r[0] = u, r[1] = h
                }
            }, e.fromLine = function (t, e, r, n, i, s) {
                i[0] = o(t, r), i[1] = o(e, n), s[0] = a(t, r), s[1] = a(e, n)
            }, e.fromCubic = function (t, e, r, n, s, u, l, h, c, f) {
                var v, g = i.cubicExtrema,
                    _ = i.cubicAt,
                    y = g(t, r, s, l, d);
                for (c[0] = 1 / 0, c[1] = 1 / 0, f[0] = -1 / 0, f[1] = -1 / 0, v = 0; v < y; v++) {
                    var m = _(t, r, s, l, d[v]);
                    c[0] = o(m, c[0]), f[0] = a(m, f[0])
                }
                for (y = g(e, n, u, h, p), v = 0; v < y; v++) {
                    var x = _(e, n, u, h, p[v]);
                    c[1] = o(x, c[1]), f[1] = a(x, f[1])
                }
                c[0] = o(t, c[0]), f[0] = a(t, f[0]), c[0] = o(l, c[0]), f[0] = a(l, f[0]), c[1] = o(e, c[1]), f[1] = a(e, f[1]), c[1] = o(h, c[1]), f[1] = a(h, f[1])
            }, e.fromQuadratic = function (t, e, r, n, s, u, l, h) {
                var c = i.quadraticExtremum,
                    f = i.quadraticAt,
                    d = a(o(c(t, r, s), 1), 0),
                    p = a(o(c(e, n, u), 1), 0),
                    v = f(t, r, s, d),
                    g = f(e, n, u, p);
                l[0] = o(t, s, v), l[1] = o(e, u, g), h[0] = a(t, s, v), h[1] = a(e, u, g)
            }, e.fromArc = function (t, e, r, i, o, a, d, p, v) {
                var g = n.min,
                    _ = n.max,
                    y = Math.abs(o - a);
                if (y % l < 1e-4 && y > 1e-4) return p[0] = t - r, p[1] = e - i, v[0] = t + r, void(v[1] = e + i);
                if (h[0] = u(o) * r + t, h[1] = s(o) * i + e, c[0] = u(a) * r + t, c[1] = s(a) * i + e, g(p, h, c), _(v, h, c), (o %= l) < 0 && (o += l), (a %= l) < 0 && (a += l), o > a && !d ? a += l : o < a && d && (o += l), d) {
                    var m = a;
                    a = o, o = m
                }
                for (var x = 0; x < a; x += Math.PI / 2) x > o && (f[0] = u(x) * r + t, f[1] = s(x) * i + e, g(p, f, p), _(v, f, v))
            }
        },
        function (t, e, r) {
            var n = r(1),
                i = r(6),
                o = r(77),
                a = ["m", "M", "l", "L", "v", "V", "h", "H", "z", "Z", "c", "C", "q", "Q", "t", "T", "s", "S", "a", "A"],
                s = Math.sqrt,
                u = Math.sin,
                l = Math.cos,
                h = Math.PI,
                c = function (t) {
                    return Math.sqrt(t[0] * t[0] + t[1] * t[1])
                },
                f = function (t, e) {
                    return (t[0] * e[0] + t[1] * e[1]) / (c(t) * c(e))
                },
                d = function (t, e) {
                    return (t[0] * e[1] < t[1] * e[0] ? -1 : 1) * Math.acos(f(t, e))
                };

            function p(t, e, r, n, i, o, a, c, p, v, g) {
                var _ = p * (h / 180),
                    y = l(_) * (t - r) / 2 + u(_) * (e - n) / 2,
                    m = -1 * u(_) * (t - r) / 2 + l(_) * (e - n) / 2,
                    x = y * y / (a * a) + m * m / (c * c);
                x > 1 && (a *= s(x), c *= s(x));
                var b = (i === o ? -1 : 1) * s((a * a * (c * c) - a * a * (m * m) - c * c * (y * y)) / (a * a * (m * m) + c * c * (y * y))) || 0,
                    w = b * a * m / c,
                    S = b * -c * y / a,
                    k = (t + r) / 2 + l(_) * w - u(_) * S,
                    T = (e + n) / 2 + u(_) * w + l(_) * S,
                    C = d([1, 0], [(y - w) / a, (m - S) / c]),
                    A = [(y - w) / a, (m - S) / c],
                    L = [(-1 * y - w) / a, (-1 * m - S) / c],
                    P = d(A, L);
                f(A, L) <= -1 && (P = h), f(A, L) >= 1 && (P = 0), 0 === o && P > 0 && (P -= 2 * h), 1 === o && P < 0 && (P += 2 * h), g.addData(v, k, T, a, c, C, P, _, o)
            }

            function v(t, e) {
                var r = function (t) {
                    if (!t) return [];
                    var e, r = t.replace(/-/g, " -").replace(/  /g, " ").replace(/ /g, ",").replace(/,,/g, ",");
                    for (e = 0; e < a.length; e++) r = r.replace(new RegExp(a[e], "g"), "|" + a[e]);
                    var n, o = r.split("|"),
                        s = 0,
                        u = 0,
                        l = new i,
                        h = i.CMD;
                    for (e = 1; e < o.length; e++) {
                        var c, f = o[e],
                            d = f.charAt(0),
                            v = 0,
                            g = f.slice(1).replace(/e,-/g, "e-").split(",");
                        g.length > 0 && "" === g[0] && g.shift();
                        for (var _ = 0; _ < g.length; _++) g[_] = parseFloat(g[_]);
                        for (; v < g.length && !isNaN(g[v]) && !isNaN(g[0]);) {
                            var y, m, x, b, w, S, k, T = s,
                                C = u;
                            switch (d) {
                            case "l":
                                s += g[v++], u += g[v++], c = h.L, l.addData(c, s, u);
                                break;
                            case "L":
                                s = g[v++], u = g[v++], c = h.L, l.addData(c, s, u);
                                break;
                            case "m":
                                s += g[v++], u += g[v++], c = h.M, l.addData(c, s, u), d = "l";
                                break;
                            case "M":
                                s = g[v++], u = g[v++], c = h.M, l.addData(c, s, u), d = "L";
                                break;
                            case "h":
                                s += g[v++], c = h.L, l.addData(c, s, u);
                                break;
                            case "H":
                                s = g[v++], c = h.L, l.addData(c, s, u);
                                break;
                            case "v":
                                u += g[v++], c = h.L, l.addData(c, s, u);
                                break;
                            case "V":
                                u = g[v++], c = h.L, l.addData(c, s, u);
                                break;
                            case "C":
                                c = h.C, l.addData(c, g[v++], g[v++], g[v++], g[v++], g[v++], g[v++]), s = g[v - 2], u = g[v - 1];
                                break;
                            case "c":
                                c = h.C, l.addData(c, g[v++] + s, g[v++] + u, g[v++] + s, g[v++] + u, g[v++] + s, g[v++] + u), s += g[v - 2], u += g[v - 1];
                                break;
                            case "S":
                                y = s, m = u;
                                var A = l.len(),
                                    L = l.data;
                                n === h.C && (y += s - L[A - 4], m += u - L[A - 3]), c = h.C, T = g[v++], C = g[v++], s = g[v++], u = g[v++], l.addData(c, y, m, T, C, s, u);
                                break;
                            case "s":
                                y = s, m = u, A = l.len(), L = l.data, n === h.C && (y += s - L[A - 4], m += u - L[A - 3]), c = h.C, T = s + g[v++], C = u + g[v++], s += g[v++], u += g[v++], l.addData(c, y, m, T, C, s, u);
                                break;
                            case "Q":
                                T = g[v++], C = g[v++], s = g[v++], u = g[v++], c = h.Q, l.addData(c, T, C, s, u);
                                break;
                            case "q":
                                T = g[v++] + s, C = g[v++] + u, s += g[v++], u += g[v++], c = h.Q, l.addData(c, T, C, s, u);
                                break;
                            case "T":
                                y = s, m = u, A = l.len(), L = l.data, n === h.Q && (y += s - L[A - 4], m += u - L[A - 3]), s = g[v++], u = g[v++], c = h.Q, l.addData(c, y, m, s, u);
                                break;
                            case "t":
                                y = s, m = u, A = l.len(), L = l.data, n === h.Q && (y += s - L[A - 4], m += u - L[A - 3]), s += g[v++], u += g[v++], c = h.Q, l.addData(c, y, m, s, u);
                                break;
                            case "A":
                                x = g[v++], b = g[v++], w = g[v++], S = g[v++], k = g[v++], p(T = s, C = u, s = g[v++], u = g[v++], S, k, x, b, w, c = h.A, l);
                                break;
                            case "a":
                                x = g[v++], b = g[v++], w = g[v++], S = g[v++], k = g[v++], p(T = s, C = u, s += g[v++], u += g[v++], S, k, x, b, w, c = h.A, l)
                            }
                        }
                        "z" !== d && "Z" !== d || (c = h.Z, l.addData(c)), n = c
                    }
                    return l.toStatic(), l
                }(t);
                return (e = e || {}).buildPath = function (t) {
                    if (t.setData) {
                        t.setData(r.data), (e = t.getContext()) && t.rebuildPath(e)
                    } else {
                        var e = t;
                        r.rebuildPath(e)
                    }
                }, e.applyTransform = function (t) {
                    o(r, t), this.dirty(!0)
                }, e
            }
            e.createFromString = function (t, e) {
                return new n(v(t, e))
            }, e.extendFromString = function (t, e) {
                return n.extend(v(t, e))
            }, e.mergePath = function (t, e) {
                for (var r = [], i = t.length, o = 0; o < i; o++) {
                    var a = t[o];
                    a.path || a.createPathProxy(), a.__dirtyPath && a.buildPath(a.path, a.shape, !0), r.push(a.path)
                }
                var s = new n(e);
                return s.createPathProxy(), s.buildPath = function (t) {
                    t.appendPath(r);
                    var e = t.getContext();
                    e && t.rebuildPath(e)
                }, s
            }
        },
        function (t, e, r) {
            var n = r(0);
            e.util = n;
            var i = r(14);
            e.matrix = i;
            var o = r(2);
            e.vector = o;
            var a = r(23);
            e.color = a;
            var s = r(85);
            e.path = s;
            var u = r(37);
            e.Group = u;
            var l = r(1);
            e.Path = l;
            var h = r(9);
            e.Image = h;
            var c = r(76);
            e.CompoundPath = c;
            var f = r(7);
            e.Text = f;
            var d = r(75);
            e.IncrementalDisplayable = d;
            var p = r(74);
            e.Arc = p;
            var v = r(73);
            e.BezierCurve = v;
            var g = r(72);
            e.Circle = g;
            var _ = r(71);
            e.Droplet = _;
            var y = r(70);
            e.Ellipse = y;
            var m = r(69);
            e.Heart = m;
            var x = r(68);
            e.Isogon = x;
            var b = r(67);
            e.Line = b;
            var w = r(66);
            e.Polygon = w;
            var S = r(63);
            e.Polyline = S;
            var k = r(62);
            e.Rect = k;
            var T = r(61);
            e.Ring = T;
            var C = r(60);
            e.Rose = C;
            var A = r(59);
            e.Sector = A;
            var L = r(57);
            e.Star = L;
            var P = r(56);
            e.Trochoid = P;
            var M = r(55);
            e.LinearGradient = M;
            var B = r(54);
            e.RadialGradient = B;
            var D = r(22);
            e.Pattern = D;
            var z = r(3);
            e.BoundingRect = z
        },
        function (t, e, r) {
            var n = r(20),
                i = function () {
                    this._track = []
                };

            function o(t) {
                var e = t[1][0] - t[0][0],
                    r = t[1][1] - t[0][1];
                return Math.sqrt(e * e + r * r)
            }
            i.prototype = {
                constructor: i,
                recognize: function (t, e, r) {
                    return this._doTrack(t, e, r), this._recognize(t)
                }, clear: function () {
                    return this._track.length = 0, this
                }, _doTrack: function (t, e, r) {
                    var i = t.touches;
                    if (i) {
                        for (var o = {
                            points: [],
                            touches: [],
                            target: e,
                            event: t
                        }, a = 0, s = i.length; a < s; a++) {
                            var u = i[a],
                                l = n.clientToLocal(r, u, {});
                            o.points.push([l.zrX, l.zrY]), o.touches.push(u)
                        }
                        this._track.push(o)
                    }
                }, _recognize: function (t) {
                    for (var e in a)
                        if (a.hasOwnProperty(e)) {
                            var r = a[e](this._track, t);
                            if (r) return r
                        }
                }
            };
            var a = {
                    pinch: function (t, e) {
                        var r = t.length;
                        if (r) {
                            var n, i = (t[r - 1] || {}).points,
                                a = (t[r - 2] || {}).points || i;
                            if (a && a.length > 1 && i && i.length > 1) {
                                var s = o(i) / o(a);
                                !isFinite(s) && (s = 1), e.pinchScale = s;
                                var u = [((n = i)[0][0] + n[1][0]) / 2, (n[0][1] + n[1][1]) / 2];
                                return e.pinchX = u[0], e.pinchY = u[1], {
                                    type: "pinch",
                                    target: t[0].target,
                                    event: e
                                }
                            }
                        }
                    }
                },
                s = i;
            t.exports = s
        },
        function (t, e, r) {
            var n = r(20),
                i = n.addEventListener,
                o = n.removeEventListener,
                a = n.normalizeEvent,
                s = r(0),
                u = r(15),
                l = r(4),
                h = r(87),
                c = ["click", "dblclick", "mousewheel", "mouseout", "mouseup", "mousedown", "mousemove", "contextmenu"],
                f = ["touchstart", "touchend", "touchmove"],
                d = {
                    pointerdown: 1,
                    pointerup: 1,
                    pointermove: 1,
                    pointerout: 1
                },
                p = s.map(c, function (t) {
                    var e = t.replace("mouse", "pointer");
                    return d[e] ? e : t
                });

            function v(t) {
                return "mousewheel" === t && l.browser.firefox ? "DOMMouseScroll" : t
            }

            function g(t, e, r) {
                var n = t._gestureMgr;
                "start" === r && n.clear();
                var i = n.recognize(e, t.handler.findHover(e.zrX, e.zrY, null).target, t.dom);
                if ("end" === r && n.clear(), i) {
                    var o = i.type;
                    e.gestureEvent = o, t.handler.dispatchToElement({
                        target: i.target
                    }, o, i.event)
                }
            }

            function _(t) {
                t._touching = !0, clearTimeout(t._touchTimer), t._touchTimer = setTimeout(function () {
                    t._touching = !1
                }, 700)
            }
            var y = {
                mousemove: function (t) {
                    t = a(this.dom, t), this.trigger("mousemove", t)
                }, mouseout: function (t) {
                    var e = (t = a(this.dom, t)).toElement || t.relatedTarget;
                    if (e != this.dom)
                        for (; e && 9 != e.nodeType;) {
                            if (e === this.dom) return;
                            e = e.parentNode
                        }
                    this.trigger("mouseout", t)
                }, touchstart: function (t) {
                    (t = a(this.dom, t)).zrByTouch = !0, this._lastTouchMoment = new Date, g(this, t, "start"), y.mousemove.call(this, t), y.mousedown.call(this, t), _(this)
                }, touchmove: function (t) {
                    (t = a(this.dom, t)).zrByTouch = !0, g(this, t, "change"), y.mousemove.call(this, t), _(this)
                }, touchend: function (t) {
                    (t = a(this.dom, t)).zrByTouch = !0, g(this, t, "end"), y.mouseup.call(this, t), +new Date - this._lastTouchMoment < 300 && y.click.call(this, t), _(this)
                }, pointerdown: function (t) {
                    y.mousedown.call(this, t)
                }, pointermove: function (t) {
                    m(t) || y.mousemove.call(this, t)
                }, pointerup: function (t) {
                    y.mouseup.call(this, t)
                }, pointerout: function (t) {
                    m(t) || y.mouseout.call(this, t)
                }
            };

            function m(t) {
                var e = t.pointerType;
                return "pen" === e || "touch" === e
            }

            function x(t) {
                var e;

                function r(e, r) {
                    s.each(e, function (e) {
                        i(t, v(e), r._handlers[e])
                    }, r)
                }
                u.call(this), this.dom = t, this._touching = !1, this._touchTimer, this._gestureMgr = new h, this._handlers = {}, e = this, s.each(f, function (t) {
                    e._handlers[t] = s.bind(y[t], e)
                }), s.each(p, function (t) {
                    e._handlers[t] = s.bind(y[t], e)
                }), s.each(c, function (t) {
                    e._handlers[t] = function (t, e) {
                        return function () {
                            if (!e._touching) return t.apply(e, arguments)
                        }
                    }(y[t], e)
                }), l.pointerEventsSupported ? r(p, this) : (l.touchEventsSupported && r(f, this), r(c, this))
            }
            s.each(["click", "mousedown", "mouseup", "mousewheel", "dblclick", "contextmenu"], function (t) {
                y[t] = function (e) {
                    e = a(this.dom, e), this.trigger(t, e)
                }
            });
            var b = x.prototype;
            b.dispose = function () {
                for (var t = c.concat(f), e = 0; e < t.length; e++) {
                    var r = t[e];
                    o(this.dom, v(r), this._handlers[r])
                }
            }, b.setCursor = function (t) {
                this.dom.style && (this.dom.style.cursor = t || "default")
            }, s.mixin(x, u);
            var w = x;
            t.exports = w
        },
        function (t, e, r) {
            var n = r(0),
                i = r(20).Dispatcher,
                o = r(30),
                a = r(35),
                s = function (t) {
                    t = t || {}, this.stage = t.stage || {}, this.onframe = t.onframe || function () {}, this._clips = [], this._running = !1, this._time, this._pausedTime, this._pauseStart, this._paused = !1, i.call(this)
                };
            s.prototype = {
                constructor: s,
                addClip: function (t) {
                    this._clips.push(t)
                }, addAnimator: function (t) {
                    t.animation = this;
                    for (var e = t.getClips(), r = 0; r < e.length; r++) this.addClip(e[r])
                }, removeClip: function (t) {
                    var e = n.indexOf(this._clips, t);
                    e >= 0 && this._clips.splice(e, 1)
                }, removeAnimator: function (t) {
                    for (var e = t.getClips(), r = 0; r < e.length; r++) this.removeClip(e[r]);
                    t.animation = null
                }, _update: function () {
                    for (var t = (new Date).getTime() - this._pausedTime, e = t - this._time, r = this._clips, n = r.length, i = [], o = [], a = 0; a < n; a++) {
                        var s = r[a],
                            u = s.step(t, e);
                        u && (i.push(u), o.push(s))
                    }
                    for (a = 0; a < n;) r[a]._needsRemove ? (r[a] = r[n - 1], r.pop(), n--) : a++;
                    n = i.length;
                    for (a = 0; a < n; a++) o[a].fire(i[a]);
                    this._time = t, this.onframe(e), this.trigger("frame", e), this.stage.update && this.stage.update()
                }, _startLoop: function () {
                    var t = this;
                    this._running = !0, o(function e() {
                        t._running && (o(e), !t._paused && t._update())
                    })
                }, start: function () {
                    this._time = (new Date).getTime(), this._pausedTime = 0, this._startLoop()
                }, stop: function () {
                    this._running = !1
                }, pause: function () {
                    this._paused || (this._pauseStart = (new Date).getTime(), this._paused = !0)
                }, resume: function () {
                    this._paused && (this._pausedTime += (new Date).getTime() - this._pauseStart, this._paused = !1)
                }, clear: function () {
                    this._clips = []
                }, isFinished: function () {
                    return !this._clips.length
                }, animate: function (t, e) {
                    var r = new a(t, (e = e || {}).loop, e.getter, e.setter);
                    return this.addAnimator(r), r
                }
            }, n.mixin(s, i);
            var u = s;
            t.exports = u
        },
        function (t, e, r) {
            var n = r(0),
                i = r(13).devicePixelRatio,
                o = r(32),
                a = r(22);

            function s() {
                return !1
            }

            function u(t, e, r) {
                var i = n.createCanvas(),
                    o = e.getWidth(),
                    a = e.getHeight(),
                    s = i.style;
                return s && (s.position = "absolute", s.left = 0, s.top = 0, s.width = o + "px", s.height = a + "px", i.setAttribute("data-zr-dom-id", t)), i.width = o * r, i.height = a * r, i
            }
            var l = function (t, e, r) {
                var o;
                r = r || i, "string" == typeof t ? o = u(t, e, r) : n.isObject(t) && (t = (o = t).id), this.id = t, this.dom = o;
                var a = o.style;
                a && (o.onselectstart = s, a["-webkit-user-select"] = "none", a["user-select"] = "none", a["-webkit-touch-callout"] = "none", a["-webkit-tap-highlight-color"] = "rgba(0,0,0,0)", a.padding = 0, a.margin = 0, a["border-width"] = 0), this.domBack = null, this.ctxBack = null, this.painter = e, this.config = null, this.clearColor = 0, this.motionBlur = !1, this.lastFrameAlpha = .7, this.dpr = r
            };
            l.prototype = {
                constructor: l,
                __dirty: !0,
                __used: !1,
                __drawIndex: 0,
                __startIndex: 0,
                __endIndex: 0,
                incremental: !1,
                getElementCount: function () {
                    return this.__endIndex - this.__startIndex
                }, initContext: function () {
                    this.ctx = this.dom.getContext("2d"), this.ctx.dpr = this.dpr
                }, createBackBuffer: function () {
                    var t = this.dpr;
                    this.domBack = u("back-" + this.id, this.painter, t), this.ctxBack = this.domBack.getContext("2d"), 1 != t && this.ctxBack.scale(t, t)
                }, resize: function (t, e) {
                    var r = this.dpr,
                        n = this.dom,
                        i = n.style,
                        o = this.domBack;
                    i && (i.width = t + "px", i.height = e + "px"), n.width = t * r, n.height = e * r, o && (o.width = t * r, o.height = e * r, 1 != r && this.ctxBack.scale(r, r))
                }, clear: function (t, e) {
                    var r, n = this.dom,
                        i = this.ctx,
                        s = n.width,
                        u = n.height,
                        l = (e = e || this.clearColor, this.motionBlur && !t),
                        h = this.lastFrameAlpha,
                        c = this.dpr;
                    (l && (this.domBack || this.createBackBuffer(), this.ctxBack.globalCompositeOperation = "copy", this.ctxBack.drawImage(n, 0, 0, s / c, u / c)), i.clearRect(0, 0, s, u), e && "transparent" !== e) && (e.colorStops ? (r = e.__canvasGradient || o.getGradient(i, e, {
                        x: 0,
                        y: 0,
                        width: s,
                        height: u
                    }), e.__canvasGradient = r) : e.image && (r = a.prototype.getCanvasPattern.call(e, i)), i.save(), i.fillStyle = r || e, i.fillRect(0, 0, s, u), i.restore());
                    if (l) {
                        var f = this.domBack;
                        i.save(), i.globalAlpha = h, i.drawImage(f, 0, 0, s, u), i.restore()
                    }
                }
            };
            var h = l;
            t.exports = h
        },
        function (t, e, r) {
            var n = r(13).devicePixelRatio,
                i = r(0),
                o = r(10),
                a = r(3),
                s = r(33),
                u = r(90),
                l = r(30),
                h = r(9),
                c = r(4);

            function f(t) {
                return parseInt(t, 10)
            }
            var d = new a(0, 0, 0, 0),
                p = new a(0, 0, 0, 0);
            var v = function (t, e, r) {
                this.type = "canvas";
                var o = !t.nodeName || "CANVAS" === t.nodeName.toUpperCase();
                this._opts = r = i.extend({}, r || {}), this.dpr = r.devicePixelRatio || n, this._singleCanvas = o, this.root = t;
                var a = t.style;
                a && (a["-webkit-tap-highlight-color"] = "transparent", a["-webkit-user-select"] = a["user-select"] = a["-webkit-touch-callout"] = "none", t.innerHTML = ""), this.storage = e;
                var s = this._zlevelList = [],
                    l = this._layers = {};
                if (this._layerConfig = {}, this._needsManuallyCompositing = !1, o) {
                    var h = t.width,
                        c = t.height;
                    null != r.width && (h = r.width), null != r.height && (c = r.height), this.dpr = r.devicePixelRatio || 1, t.width = h * this.dpr, t.height = c * this.dpr, this._width = h, this._height = c;
                    var f = new u(t, this, this.dpr);
                    f.__builtin__ = !0, f.initContext(), l[314159] = f, s.push(314159), this._domRoot = t
                } else {
                    this._width = this._getSize(0), this._height = this._getSize(1);
                    var d = this._domRoot = function (t, e) {
                        var r = document.createElement("div");
                        return r.style.cssText = ["position:relative", "overflow:hidden", "width:" + t + "px", "height:" + e + "px", "padding:0", "margin:0", "border-width:0"].join(";") + ";", r
                    }(this._width, this._height);
                    t.appendChild(d)
                }
                this._hoverlayer = null, this._hoverElements = []
            };
            v.prototype = {
                constructor: v,
                getType: function () {
                    return "canvas"
                }, isSingleCanvas: function () {
                    return this._singleCanvas
                }, getViewportRoot: function () {
                    return this._domRoot
                }, getViewportRootOffset: function () {
                    var t = this.getViewportRoot();
                    if (t) return {
                        offsetLeft: t.offsetLeft || 0,
                        offsetTop: t.offsetTop || 0
                    }
                }, refresh: function (t) {
                    var e = this.storage.getDisplayList(!0),
                        r = this._zlevelList;
                    this._redrawId = Math.random(), this._paintList(e, t, this._redrawId);
                    for (var n = 0; n < r.length; n++) {
                        var i = r[n],
                            o = this._layers[i];
                        if (!o.__builtin__ && o.refresh) {
                            var a = 0 === n ? this._backgroundColor : null;
                            o.refresh(a)
                        }
                    }
                    return this.refreshHover(), this
                }, addHover: function (t, e) {
                    if (!t.__hoverMir) {
                        var r = new t.constructor({
                            style: t.style,
                            shape: t.shape
                        });
                        r.__from = t, t.__hoverMir = r, r.setStyle(e), this._hoverElements.push(r)
                    }
                }, removeHover: function (t) {
                    var e = t.__hoverMir,
                        r = this._hoverElements,
                        n = i.indexOf(r, e);
                    n >= 0 && r.splice(n, 1), t.__hoverMir = null
                }, clearHover: function (t) {
                    for (var e = this._hoverElements, r = 0; r < e.length; r++) {
                        var n = e[r].__from;
                        n && (n.__hoverMir = null)
                    }
                    e.length = 0
                }, refreshHover: function () {
                    var t = this._hoverElements,
                        e = t.length,
                        r = this._hoverlayer;
                    if (r && r.clear(), e) {
                        s(t, this.storage.displayableSortFunc), r || (r = this._hoverlayer = this.getLayer(1e5));
                        var n = {};
                        r.ctx.save();
                        for (var i = 0; i < e;) {
                            var o = t[i],
                                a = o.__from;
                            a && a.__zr ? (i++, a.invisible || (o.transform = a.transform, o.invTransform = a.invTransform, o.__clipPaths = a.__clipPaths, this._doPaintEl(o, r, !0, n))) : (t.splice(i, 1), a.__hoverMir = null, e--)
                        }
                        r.ctx.restore()
                    }
                }, getHoverLayer: function () {
                    return this.getLayer(1e5)
                }, _paintList: function (t, e, r) {
                    if (this._redrawId === r) {
                        e = e || !1, this._updateLayerStatus(t);
                        var n = this._doPaintList(t, e);
                        if (this._needsManuallyCompositing && this._compositeManually(), !n) {
                            var i = this;
                            l(function () {
                                i._paintList(t, e, r)
                            })
                        }
                    }
                }, _compositeManually: function () {
                    var t = this.getLayer(314159).ctx,
                        e = this._domRoot.width,
                        r = this._domRoot.height;
                    t.clearRect(0, 0, e, r), this.eachBuiltinLayer(function (n) {
                        n.virtual && t.drawImage(n.dom, 0, 0, e, r)
                    })
                }, _doPaintList: function (t, e) {
                    for (var r = [], n = 0; n < this._zlevelList.length; n++) {
                        var o = this._zlevelList[n];
                        (u = this._layers[o]).__builtin__ && u !== this._hoverlayer && (u.__dirty || e) && r.push(u)
                    }
                    for (var a = !0, s = 0; s < r.length; s++) {
                        var u, l = (u = r[s]).ctx,
                            h = {};
                        l.save();
                        var f = e ? u.__startIndex : u.__drawIndex,
                            d = !e && u.incremental && Date.now,
                            p = d && Date.now(),
                            v = u.zlevel === this._zlevelList[0] ? this._backgroundColor : null;
                        if (u.__startIndex === u.__endIndex) u.clear(!1, v);
                        else if (f === u.__startIndex) {
                            var g = t[f];
                            g.incremental && g.notClear && !e || u.clear(!1, v)
                        } - 1 === f && (f = u.__startIndex);
                        for (var _ = f; _ < u.__endIndex; _++) {
                            var y = t[_];
                            if (this._doPaintEl(y, u, e, h), y.__dirty = !1, d)
                                if (Date.now() - p > 15) break
                        }
                        u.__drawIndex = _, u.__drawIndex < u.__endIndex && (a = !1), h.prevElClipPaths && l.restore(), l.restore()
                    }
                    return c.wxa && i.each(this._layers, function (t) {
                        t && t.ctx && t.ctx.draw && t.ctx.draw()
                    }), a
                }, _doPaintEl: function (t, e, r, n) {
                    var i = e.ctx,
                        o = t.transform;
                    if ((e.__dirty || r) && !t.invisible && 0 !== t.style.opacity && (!o || o[0] || o[3]) && (!t.culling || ! function (t, e, r) {
                        return d.copy(t.getBoundingRect()), t.transform && d.applyTransform(t.transform), p.width = e, p.height = r, !d.intersect(p)
                    }(t, this._width, this._height))) {
                        var a = t.__clipPaths;
                        n.prevElClipPaths && ! function (t, e) {
                            if (t == e) return !1;
                            if (!t || !e || t.length !== e.length) return !0;
                            for (var r = 0; r < t.length; r++)
                                if (t[r] !== e[r]) return !0
                        }(a, n.prevElClipPaths) || (n.prevElClipPaths && (e.ctx.restore(), n.prevElClipPaths = null, n.prevEl = null), a && (i.save(), function (t, e) {
                            for (var r = 0; r < t.length; r++) {
                                var n = t[r];
                                n.setTransform(e), e.beginPath(), n.buildPath(e, n.shape), e.clip(), n.restoreTransform(e)
                            }
                        }(a, i), n.prevElClipPaths = a)), t.beforeBrush && t.beforeBrush(i), t.brush(i, n.prevEl || null), n.prevEl = t, t.afterBrush && t.afterBrush(i)
                    }
                }, getLayer: function (t, e) {
                    this._singleCanvas && !this._needsManuallyCompositing && (t = 314159);
                    var r = this._layers[t];
                    return r || ((r = new u("zr_" + t, this, this.dpr)).zlevel = t, r.__builtin__ = !0, this._layerConfig[t] && i.merge(r, this._layerConfig[t], !0), e && (r.virtual = e), this.insertLayer(t, r), r.initContext()), r
                }, insertLayer: function (t, e) {
                    var r = this._layers,
                        n = this._zlevelList,
                        i = n.length,
                        a = null,
                        s = -1,
                        u = this._domRoot;
                    if (r[t]) o("ZLevel " + t + " has been used already");
                    else if (function (t) {
                        return !!t && (!!t.__builtin__ || "function" == typeof t.resize && "function" == typeof t.refresh)
                    }(e)) {
                        if (i > 0 && t > n[0]) {
                            for (s = 0; s < i - 1 && !(n[s] < t && n[s + 1] > t); s++);
                            a = r[n[s]]
                        }
                        if (n.splice(s + 1, 0, t), r[t] = e, !e.virtual)
                            if (a) {
                                var l = a.dom;
                                l.nextSibling ? u.insertBefore(e.dom, l.nextSibling) : u.appendChild(e.dom)
                            } else u.firstChild ? u.insertBefore(e.dom, u.firstChild) : u.appendChild(e.dom)
                    } else o("Layer of zlevel " + t + " is not valid")
                }, eachLayer: function (t, e) {
                    var r, n, i = this._zlevelList;
                    for (n = 0; n < i.length; n++) r = i[n], t.call(e, this._layers[r], r)
                }, eachBuiltinLayer: function (t, e) {
                    var r, n, i, o = this._zlevelList;
                    for (i = 0; i < o.length; i++) n = o[i], (r = this._layers[n]).__builtin__ && t.call(e, r, n)
                }, eachOtherLayer: function (t, e) {
                    var r, n, i, o = this._zlevelList;
                    for (i = 0; i < o.length; i++) n = o[i], (r = this._layers[n]).__builtin__ || t.call(e, r, n)
                }, getLayers: function () {
                    return this._layers
                }, _updateLayerStatus: function (t) {
                    function e(t) {
                        n && (n.__endIndex !== t && (n.__dirty = !0), n.__endIndex = t)
                    }
                    if (this.eachBuiltinLayer(function (t, e) {
                        t.__dirty = t.__used = !1
                    }), this._singleCanvas)
                        for (var r = 1; r < t.length; r++) {
                            if ((a = t[r]).zlevel !== t[r - 1].zlevel || a.incremental) {
                                this._needsManuallyCompositing = !0;
                                break
                            }
                        }
                    var n = null,
                        i = 0;
                    for (r = 0; r < t.length; r++) {
                        var a, s, u = (a = t[r]).zlevel;
                        a.incremental ? ((s = this.getLayer(u + .001, this._needsManuallyCompositing)).incremental = !0, i = 1) : s = this.getLayer(u + (i > 0 ? .01 : 0), this._needsManuallyCompositing), s.__builtin__ || o("ZLevel " + u + " has been used by unkown layer " + s.id), s !== n && (s.__used = !0, s.__startIndex !== r && (s.__dirty = !0), s.__startIndex = r, s.incremental ? s.__drawIndex = -1 : s.__drawIndex = r, e(r), n = s), a.__dirty && (s.__dirty = !0, s.incremental && s.__drawIndex < 0 && (s.__drawIndex = r))
                    }
                    e(r), this.eachBuiltinLayer(function (t, e) {
                        !t.__used && t.getElementCount() > 0 && (t.__dirty = !0, t.__startIndex = t.__endIndex = t.__drawIndex = 0), t.__dirty && t.__drawIndex < 0 && (t.__drawIndex = t.__startIndex)
                    })
                }, clear: function () {
                    return this.eachBuiltinLayer(this._clearLayer), this
                }, _clearLayer: function (t) {
                    t.clear()
                }, setBackgroundColor: function (t) {
                    this._backgroundColor = t
                }, configLayer: function (t, e) {
                    if (e) {
                        var r = this._layerConfig;
                        r[t] ? i.merge(r[t], e, !0) : r[t] = e;
                        for (var n = 0; n < this._zlevelList.length; n++) {
                            var o = this._zlevelList[n];
                            if (o === t || o === t + .01) {
                                var a = this._layers[o];
                                i.merge(a, r[t], !0)
                            }
                        }
                    }
                }, delLayer: function (t) {
                    var e = this._layers,
                        r = this._zlevelList,
                        n = e[t];
                    n && (n.dom.parentNode.removeChild(n.dom), delete e[t], r.splice(i.indexOf(r, t), 1))
                }, resize: function (t, e) {
                    if (this._domRoot.style) {
                        var r = this._domRoot;
                        r.style.display = "none";
                        var n = this._opts;
                        if (null != t && (n.width = t), null != e && (n.height = e), t = this._getSize(0), e = this._getSize(1), r.style.display = "", this._width != t || e != this._height) {
                            for (var o in r.style.width = t + "px", r.style.height = e + "px", this._layers) this._layers.hasOwnProperty(o) && this._layers[o].resize(t, e);
                            i.each(this._progressiveLayers, function (r) {
                                r.resize(t, e)
                            }), this.refresh(!0)
                        }
                        this._width = t, this._height = e
                    } else {
                        if (null == t || null == e) return;
                        this._width = t, this._height = e, this.getLayer(314159).resize(t, e)
                    }
                    return this
                }, clearLayer: function (t) {
                    var e = this._layers[t];
                    e && e.clear()
                }, dispose: function () {
                    this.root.innerHTML = "", this.root = this.storage = this._domRoot = this._layers = null
                }, getRenderedCanvas: function (t) {
                    if (t = t || {}, this._singleCanvas && !this._compositeManually) return this._layers[314159].dom;
                    var e = new u("image", this, t.pixelRatio || this.dpr);
                    if (e.initContext(), e.clear(!1, t.backgroundColor || this._backgroundColor), t.pixelRatio <= this.dpr) {
                        this.refresh();
                        var r = e.dom.width,
                            n = e.dom.height,
                            i = e.ctx;
                        this.eachLayer(function (t) {
                            t.__builtin__ ? i.drawImage(t.dom, 0, 0, r, n) : t.renderToCanvas && (e.ctx.save(), t.renderToCanvas(e.ctx), e.ctx.restore())
                        })
                    } else
                        for (var o = {}, a = this.storage.getDisplayList(!0), s = 0; s < a.length; s++) {
                            var l = a[s];
                            this._doPaintEl(l, e, !0, o)
                        }
                    return e.dom
                }, getWidth: function () {
                    return this._width
                }, getHeight: function () {
                    return this._height
                }, _getSize: function (t) {
                    var e = this._opts,
                        r = ["width", "height"][t],
                        n = ["clientWidth", "clientHeight"][t],
                        i = ["paddingLeft", "paddingTop"][t],
                        o = ["paddingRight", "paddingBottom"][t];
                    if (null != e[r] && "auto" !== e[r]) return parseFloat(e[r]);
                    var a = this.root,
                        s = document.defaultView.getComputedStyle(a);
                    return (a[n] || f(s[r]) || f(a.style[r])) - (f(s[i]) || 0) - (f(s[o]) || 0) | 0
                }, pathToImage: function (t, e) {
                    e = e || this.dpr;
                    var r = document.createElement("canvas"),
                        n = r.getContext("2d"),
                        i = t.getBoundingRect(),
                        o = t.style,
                        a = o.shadowBlur * e,
                        s = o.shadowOffsetX * e,
                        u = o.shadowOffsetY * e,
                        l = o.hasStroke() ? o.lineWidth : 0,
                        c = Math.max(l / 2, -s + a),
                        f = Math.max(l / 2, s + a),
                        d = Math.max(l / 2, -u + a),
                        p = Math.max(l / 2, u + a),
                        v = i.width + c + f,
                        g = i.height + d + p;
                    r.width = v * e, r.height = g * e, n.scale(e, e), n.clearRect(0, 0, v, g), n.dpr = e;
                    var _ = {
                        position: t.position,
                        rotation: t.rotation,
                        scale: t.scale
                    };
                    t.position = [c - i.x, d - i.y], t.rotation = 0, t.scale = [1, 1], t.updateTransform(), t && t.brush(n);
                    var y = new h({
                        style: {
                            x: 0,
                            y: 0,
                            image: r
                        }
                    });
                    return null != _.position && (y.position = t.position = _.position), null != _.rotation && (y.rotation = t.rotation = _.rotation), null != _.scale && (y.scale = t.scale = _.scale), y
                }
            };
            var g = v;
            t.exports = g
        },
        function (t, e) {
            var r = {
                    linear: function (t) {
                        return t
                    }, quadraticIn: function (t) {
                        return t * t
                    }, quadraticOut: function (t) {
                        return t * (2 - t)
                    }, quadraticInOut: function (t) {
                        return (t *= 2) < 1 ? .5 * t * t : -.5 * (--t * (t - 2) - 1)
                    }, cubicIn: function (t) {
                        return t * t * t
                    }, cubicOut: function (t) {
                        return --t * t * t + 1
                    }, cubicInOut: function (t) {
                        return (t *= 2) < 1 ? .5 * t * t * t : .5 * ((t -= 2) * t * t + 2)
                    }, quarticIn: function (t) {
                        return t * t * t * t
                    }, quarticOut: function (t) {
                        return 1 - --t * t * t * t
                    }, quarticInOut: function (t) {
                        return (t *= 2) < 1 ? .5 * t * t * t * t : -.5 * ((t -= 2) * t * t * t - 2)
                    }, quinticIn: function (t) {
                        return t * t * t * t * t
                    }, quinticOut: function (t) {
                        return --t * t * t * t * t + 1
                    }, quinticInOut: function (t) {
                        return (t *= 2) < 1 ? .5 * t * t * t * t * t : .5 * ((t -= 2) * t * t * t * t + 2)
                    }, sinusoidalIn: function (t) {
                        return 1 - Math.cos(t * Math.PI / 2)
                    }, sinusoidalOut: function (t) {
                        return Math.sin(t * Math.PI / 2)
                    }, sinusoidalInOut: function (t) {
                        return .5 * (1 - Math.cos(Math.PI * t))
                    }, exponentialIn: function (t) {
                        return 0 === t ? 0 : Math.pow(1024, t - 1)
                    }, exponentialOut: function (t) {
                        return 1 === t ? 1 : 1 - Math.pow(2, -10 * t)
                    }, exponentialInOut: function (t) {
                        return 0 === t ? 0 : 1 === t ? 1 : (t *= 2) < 1 ? .5 * Math.pow(1024, t - 1) : .5 * (2 - Math.pow(2, -10 * (t - 1)))
                    }, circularIn: function (t) {
                        return 1 - Math.sqrt(1 - t * t)
                    }, circularOut: function (t) {
                        return Math.sqrt(1 - --t * t)
                    }, circularInOut: function (t) {
                        return (t *= 2) < 1 ? -.5 * (Math.sqrt(1 - t * t) - 1) : .5 * (Math.sqrt(1 - (t -= 2) * t) + 1)
                    }, elasticIn: function (t) {
                        var e, r = .1;
                        return 0 === t ? 0 : 1 === t ? 1 : (!r || r < 1 ? (r = 1, e = .1) : e = .4 * Math.asin(1 / r) / (2 * Math.PI), -r * Math.pow(2, 10 * (t -= 1)) * Math.sin((t - e) * (2 * Math.PI) / .4))
                    }, elasticOut: function (t) {
                        var e, r = .1;
                        return 0 === t ? 0 : 1 === t ? 1 : (!r || r < 1 ? (r = 1, e = .1) : e = .4 * Math.asin(1 / r) / (2 * Math.PI), r * Math.pow(2, -10 * t) * Math.sin((t - e) * (2 * Math.PI) / .4) + 1)
                    }, elasticInOut: function (t) {
                        var e, r = .1;
                        return 0 === t ? 0 : 1 === t ? 1 : (!r || r < 1 ? (r = 1, e = .1) : e = .4 * Math.asin(1 / r) / (2 * Math.PI), (t *= 2) < 1 ? r * Math.pow(2, 10 * (t -= 1)) * Math.sin((t - e) * (2 * Math.PI) / .4) * -.5 : r * Math.pow(2, -10 * (t -= 1)) * Math.sin((t - e) * (2 * Math.PI) / .4) * .5 + 1)
                    }, backIn: function (t) {
                        var e = 1.70158;
                        return t * t * ((e + 1) * t - e)
                    }, backOut: function (t) {
                        var e = 1.70158;
                        return --t * t * ((e + 1) * t + e) + 1
                    }, backInOut: function (t) {
                        var e = 2.5949095;
                        return (t *= 2) < 1 ? t * t * ((e + 1) * t - e) * .5 : .5 * ((t -= 2) * t * ((e + 1) * t + e) + 2)
                    }, bounceIn: function (t) {
                        return 1 - r.bounceOut(1 - t)
                    }, bounceOut: function (t) {
                        return t < 1 / 2.75 ? 7.5625 * t * t : t < 2 / 2.75 ? 7.5625 * (t -= 1.5 / 2.75) * t + .75 : t < 2.5 / 2.75 ? 7.5625 * (t -= 2.25 / 2.75) * t + .9375 : 7.5625 * (t -= 2.625 / 2.75) * t + .984375
                    }, bounceInOut: function (t) {
                        return t < .5 ? .5 * r.bounceIn(2 * t) : .5 * r.bounceOut(2 * t - 1) + .5
                    }
                },
                n = r;
            t.exports = n
        },
        function (t, e, r) {
            var n = r(92);

            function i(t) {
                this._target = t.target, this._life = t.life || 1e3, this._delay = t.delay || 0, this._initialized = !1, this.loop = null != t.loop && t.loop, this.gap = t.gap || 0, this.easing = t.easing || "Linear", this.onframe = t.onframe, this.ondestroy = t.ondestroy, this.onrestart = t.onrestart, this._pausedTime = 0, this._paused = !1
            }
            i.prototype = {
                constructor: i,
                step: function (t, e) {
                    if (this._initialized || (this._startTime = t + this._delay, this._initialized = !0), this._paused) this._pausedTime += e;
                    else {
                        var r = (t - this._startTime - this._pausedTime) / this._life;
                        if (!(r < 0)) {
                            r = Math.min(r, 1);
                            var i = this.easing,
                                o = "string" == typeof i ? n[i] : i,
                                a = "function" == typeof o ? o(r) : r;
                            return this.fire("frame", a), 1 == r ? this.loop ? (this.restart(t), "restart") : (this._needsRemove = !0, "destroy") : null
                        }
                    }
                }, restart: function (t) {
                    var e = (t - this._startTime - this._pausedTime) % this._life;
                    this._startTime = t - e + this.gap, this._pausedTime = 0, this._needsRemove = !1
                }, fire: function (t, e) {
                    this[t = "on" + t] && this[t](this._target, e)
                }, pause: function () {
                    this._paused = !0
                }, resume: function () {
                    this._paused = !1
                }
            };
            var o = i;
            t.exports = o
        },
        function (t, e, r) {
            var n = r(35),
                i = r(10),
                o = r(0),
                a = o.isString,
                s = o.isFunction,
                u = o.isObject,
                l = o.isArrayLike,
                h = o.indexOf,
                c = function () {
                    this.animators = []
                };
            c.prototype = {
                constructor: c,
                animate: function (t, e) {
                    var r, o = !1,
                        a = this,
                        s = this.__zr;
                    if (t) {
                        var u = t.split("."),
                            l = a;
                        o = "shape" === u[0];
                        for (var c = 0, f = u.length; c < f; c++) l && (l = l[u[c]]);
                        l && (r = l)
                    } else r = a; if (r) {
                        var d = a.animators,
                            p = new n(r, e);
                        return p.during(function (t) {
                            a.dirty(o)
                        }).done(function () {
                            d.splice(h(d, p), 1)
                        }), d.push(p), s && s.animation.addAnimator(p), p
                    }
                    i('Property "' + t + '" is not existed in element ' + a.id)
                }, stopAnimation: function (t) {
                    for (var e = this.animators, r = e.length, n = 0; n < r; n++) e[n].stop(t);
                    return e.length = 0, this
                }, animateTo: function (t, e, r, n, i, o) {
                    a(r) ? (i = n, n = r, r = 0) : s(n) ? (i = n, n = "linear", r = 0) : s(r) ? (i = r, r = 0) : s(e) ? (i = e, e = 500) : e || (e = 500), this.stopAnimation(), this._animateToShallow("", this, t, e, r);
                    var u = this.animators.slice(),
                        l = u.length;

                    function h() {
                        --l || i && i()
                    }
                    l || i && i();
                    for (var c = 0; c < u.length; c++) u[c].done(h).start(n, o)
                }, _animateToShallow: function (t, e, r, n, i) {
                    var o = {},
                        a = 0;
                    for (var s in r)
                        if (r.hasOwnProperty(s))
                            if (null != e[s]) u(r[s]) && !l(r[s]) ? this._animateToShallow(t ? t + "." + s : s, e[s], r[s], n, i) : (o[s] = r[s], a++);
                            else if (null != r[s])
                        if (t) {
                            var h = {};
                            h[t] = {}, h[t][s] = r[s], this.attr(h)
                        } else this.attr(s, r[s]);
                    return a > 0 && this.animate(t, !1).when(null == n ? 500 : n, o).delay(i || 0), this
                }
            };
            var f = c;
            t.exports = f
        },
        function (t, e, r) {
            var n = r(14),
                i = r(2),
                o = n.identity,
                a = 5e-5;

            function s(t) {
                return t > a || t < -a
            }
            var u = function (t) {
                    (t = t || {}).position || (this.position = [0, 0]), null == t.rotation && (this.rotation = 0), t.scale || (this.scale = [1, 1]), this.origin = this.origin || null
                },
                l = u.prototype;
            l.transform = null, l.needLocalTransform = function () {
                return s(this.rotation) || s(this.position[0]) || s(this.position[1]) || s(this.scale[0] - 1) || s(this.scale[1] - 1)
            }, l.updateTransform = function () {
                var t = this.parent,
                    e = t && t.transform,
                    r = this.needLocalTransform(),
                    i = this.transform;
                r || e ? (i = i || n.create(), r ? this.getLocalTransform(i) : o(i), e && (r ? n.mul(i, t.transform, i) : n.copy(i, t.transform)), this.transform = i, this.invTransform = this.invTransform || n.create(), n.invert(this.invTransform, i)) : i && o(i)
            }, l.getLocalTransform = function (t) {
                return u.getLocalTransform(this, t)
            }, l.setTransform = function (t) {
                var e = this.transform,
                    r = t.dpr || 1;
                e ? t.setTransform(r * e[0], r * e[1], r * e[2], r * e[3], r * e[4], r * e[5]) : t.setTransform(r, 0, 0, r, 0, 0)
            }, l.restoreTransform = function (t) {
                var e = t.dpr || 1;
                t.setTransform(e, 0, 0, e, 0, 0)
            };
            var h = [];
            l.decomposeTransform = function () {
                if (this.transform) {
                    var t = this.parent,
                        e = this.transform;
                    t && t.transform && (n.mul(h, t.invTransform, e), e = h);
                    var r = e[0] * e[0] + e[1] * e[1],
                        i = e[2] * e[2] + e[3] * e[3],
                        o = this.position,
                        a = this.scale;
                    s(r - 1) && (r = Math.sqrt(r)), s(i - 1) && (i = Math.sqrt(i)), e[0] < 0 && (r = -r), e[3] < 0 && (i = -i), o[0] = e[4], o[1] = e[5], a[0] = r, a[1] = i, this.rotation = Math.atan2(-e[1] / i, e[0] / r)
                }
            }, l.getGlobalScale = function () {
                var t = this.transform;
                if (!t) return [1, 1];
                var e = Math.sqrt(t[0] * t[0] + t[1] * t[1]),
                    r = Math.sqrt(t[2] * t[2] + t[3] * t[3]);
                return t[0] < 0 && (e = -e), t[3] < 0 && (r = -r), [e, r]
            }, l.transformCoordToLocal = function (t, e) {
                var r = [t, e],
                    n = this.invTransform;
                return n && i.applyTransform(r, r, n), r
            }, l.transformCoordToGlobal = function (t, e) {
                var r = [t, e],
                    n = this.transform;
                return n && i.applyTransform(r, r, n), r
            }, u.getLocalTransform = function (t, e) {
                o(e = e || []);
                var r = t.origin,
                    i = t.scale || [1, 1],
                    a = t.rotation || 0,
                    s = t.position || [0, 0];
                return r && (e[4] -= r[0], e[5] -= r[1]), n.scale(e, e, i), a && n.rotate(e, e, a), r && (e[4] += r[0], e[5] += r[1]), e[4] += s[0], e[5] += s[1], e
            };
            var c = u;
            t.exports = c
        },
        function (t, e, r) {
            var n = r(0),
                i = r(4),
                o = r(37),
                a = r(33);

            function s(t, e) {
                return t.zlevel === e.zlevel ? t.z === e.z ? t.z2 - e.z2 : t.z - e.z : t.zlevel - e.zlevel
            }
            var u = function () {
                this._roots = [], this._displayList = [], this._displayListLen = 0
            };
            u.prototype = {
                constructor: u,
                traverse: function (t, e) {
                    for (var r = 0; r < this._roots.length; r++) this._roots[r].traverse(t, e)
                }, getDisplayList: function (t, e) {
                    return e = e || !1, t && this.updateDisplayList(e), this._displayList
                }, updateDisplayList: function (t) {
                    this._displayListLen = 0;
                    for (var e = this._roots, r = this._displayList, n = 0, o = e.length; n < o; n++) this._updateAndAddDisplayable(e[n], null, t);
                    r.length = this._displayListLen, i.canvasSupported && a(r, s)
                }, _updateAndAddDisplayable: function (t, e, r) {
                    if (!t.ignore || r) {
                        t.beforeUpdate(), t.__dirty && t.update(), t.afterUpdate();
                        var n = t.clipPath;
                        if (n) {
                            e = e ? e.slice() : [];
                            for (var i = n, o = t; i;) i.parent = o, i.updateTransform(), e.push(i), o = i, i = i.clipPath
                        }
                        if (t.isGroup) {
                            for (var a = t._children, s = 0; s < a.length; s++) {
                                var u = a[s];
                                t.__dirty && (u.__dirty = !0), this._updateAndAddDisplayable(u, e, r)
                            }
                            t.__dirty = !1
                        } else t.__clipPaths = e, this._displayList[this._displayListLen++] = t
                    }
                }, addRoot: function (t) {
                    t.__storage !== this && (t instanceof o && t.addChildrenToStorage(this), this.addToStorage(t), this._roots.push(t))
                }, delRoot: function (t) {
                    if (null == t) {
                        for (var e = 0; e < this._roots.length; e++) {
                            var r = this._roots[e];
                            r instanceof o && r.delChildrenFromStorage(this)
                        }
                        return this._roots = [], this._displayList = [], void(this._displayListLen = 0)
                    }
                    if (t instanceof Array) {
                        e = 0;
                        for (var i = t.length; e < i; e++) this.delRoot(t[e])
                    } else {
                        var a = n.indexOf(this._roots, t);
                        a >= 0 && (this.delFromStorage(t), this._roots.splice(a, 1), t instanceof o && t.delChildrenFromStorage(this))
                    }
                }, addToStorage: function (t) {
                    return t && (t.__storage = this, t.dirty(!1)), this
                }, delFromStorage: function (t) {
                    return t && (t.__storage = null), this
                }, dispose: function () {
                    this._renderList = this._roots = null
                }, displayableSortFunc: s
            };
            var l = u;
            t.exports = l
        },
        function (t, e) {
            function r() {
                this.on("mousedown", this._dragStart, this), this.on("mousemove", this._drag, this), this.on("mouseup", this._dragEnd, this), this.on("globalout", this._dragEnd, this)
            }

            function n(t, e) {
                return {
                    target: t,
                    topTarget: e && e.topTarget
                }
            }
            r.prototype = {
                constructor: r,
                _dragStart: function (t) {
                    var e = t.target;
                    e && e.draggable && (this._draggingTarget = e, e.dragging = !0, this._x = t.offsetX, this._y = t.offsetY, this.dispatchToElement(n(e, t), "dragstart", t.event))
                }, _drag: function (t) {
                    var e = this._draggingTarget;
                    if (e) {
                        var r = t.offsetX,
                            i = t.offsetY,
                            o = r - this._x,
                            a = i - this._y;
                        this._x = r, this._y = i, e.drift(o, a, t), this.dispatchToElement(n(e, t), "drag", t.event);
                        var s = this.findHover(r, i, e).target,
                            u = this._dropTarget;
                        this._dropTarget = s, e !== s && (u && s !== u && this.dispatchToElement(n(u, t), "dragleave", t.event), s && s !== u && this.dispatchToElement(n(s, t), "dragenter", t.event))
                    }
                }, _dragEnd: function (t) {
                    var e = this._draggingTarget;
                    e && (e.dragging = !1), this.dispatchToElement(n(e, t), "dragend", t.event), this._dropTarget && this.dispatchToElement(n(this._dropTarget, t), "drop", t.event), this._draggingTarget = null, this._dropTarget = null
                }
            };
            var i = r;
            t.exports = i
        },
        function (t, e, r) {
            var n = r(0),
                i = r(2),
                o = r(97),
                a = r(15),
                s = "silent";

            function u() {}
            u.prototype.dispose = function () {};
            var l = ["click", "dblclick", "mousewheel", "mouseout", "mouseup", "mousedown", "mousemove", "contextmenu"],
                h = function (t, e, r, n) {
                    a.call(this), this.storage = t, this.painter = e, this.painterRoot = n, r = r || new u, this.proxy = null, this._hovered = {}, this._lastTouchMoment, this._lastX, this._lastY, o.call(this), this.setHandlerProxy(r)
                };

            function c(t, e, r) {
                if (t[t.rectHover ? "rectContain" : "contain"](e, r)) {
                    for (var n, i = t; i;) {
                        if (i.clipPath && !i.clipPath.contain(e, r)) return !1;
                        i.silent && (n = !0), i = i.parent
                    }
                    return !n || s
                }
                return !1
            }
            h.prototype = {
                constructor: h,
                setHandlerProxy: function (t) {
                    this.proxy && this.proxy.dispose(), t && (n.each(l, function (e) {
                        t.on && t.on(e, this[e], this)
                    }, this), t.handler = this), this.proxy = t
                }, mousemove: function (t) {
                    var e = t.zrX,
                        r = t.zrY,
                        n = this._hovered,
                        i = n.target;
                    i && !i.__zr && (i = (n = this.findHover(n.x, n.y)).target);
                    var o = this._hovered = this.findHover(e, r),
                        a = o.target,
                        s = this.proxy;
                    s.setCursor && s.setCursor(a ? a.cursor : "default"), i && a !== i && this.dispatchToElement(n, "mouseout", t), this.dispatchToElement(o, "mousemove", t), a && a !== i && this.dispatchToElement(o, "mouseover", t)
                }, mouseout: function (t) {
                    this.dispatchToElement(this._hovered, "mouseout", t);
                    var e, r = t.toElement || t.relatedTarget;
                    do {
                        r = r && r.parentNode
                    } while (r && 9 != r.nodeType && !(e = r === this.painterRoot));
                    !e && this.trigger("globalout", {
                        event: t
                    })
                }, resize: function (t) {
                    this._hovered = {}
                }, dispatch: function (t, e) {
                    var r = this[t];
                    r && r.call(this, e)
                }, dispose: function () {
                    this.proxy.dispose(), this.storage = this.proxy = this.painter = null
                }, setCursorStyle: function (t) {
                    var e = this.proxy;
                    e.setCursor && e.setCursor(t)
                }, dispatchToElement: function (t, e, r) {
                    var n = (t = t || {}).target;
                    if (!n || !n.silent) {
                        for (var i = "on" + e, o = function (t, e, r) {
                            return {
                                type: t,
                                event: r,
                                target: e.target,
                                topTarget: e.topTarget,
                                cancelBubble: !1,
                                offsetX: r.zrX,
                                offsetY: r.zrY,
                                gestureEvent: r.gestureEvent,
                                pinchX: r.pinchX,
                                pinchY: r.pinchY,
                                pinchScale: r.pinchScale,
                                wheelDelta: r.zrDelta,
                                zrByTouch: r.zrByTouch,
                                which: r.which
                            }
                        }(e, t, r); n && (n[i] && (o.cancelBubble = n[i].call(n, o)), n.trigger(e, o), n = n.parent, !o.cancelBubble););
                        o.cancelBubble || (this.trigger(e, o), this.painter && this.painter.eachOtherLayer(function (t) {
                            "function" == typeof t[i] && t[i].call(t, o), t.trigger && t.trigger(e, o)
                        }))
                    }
                }, findHover: function (t, e, r) {
                    for (var n = this.storage.getDisplayList(), i = {
                        x: t,
                        y: e
                    }, o = n.length - 1; o >= 0; o--) {
                        var a;
                        if (n[o] !== r && !n[o].ignore && (a = c(n[o], t, e)) && (!i.topTarget && (i.topTarget = n[o]), a !== s)) {
                            i.target = n[o];
                            break
                        }
                    }
                    return i
                }
            }, n.each(["click", "mousedown", "mouseup", "mousewheel", "dblclick", "contextmenu"], function (t) {
                h.prototype[t] = function (e) {
                    var r = this.findHover(e.zrX, e.zrY),
                        n = r.target;
                    if ("mousedown" === t) this._downEl = n, this._downPoint = [e.zrX, e.zrY], this._upEl = n;
                    else if ("mouseup" === t) this._upEl = n;
                    else if ("click" === t) {
                        if (this._downEl !== this._upEl || !this._downPoint || i.dist(this._downPoint, [e.zrX, e.zrY]) > 4) return;
                        this._downPoint = null
                    }
                    this.dispatchToElement(r, t, e)
                }
            }), n.mixin(h, a), n.mixin(h, o);
            var f = h;
            t.exports = f
        },
        function (t, e) {
            t.exports = function (t) {
                return t.webpackPolyfill || (t.deprecate = function () {}, t.paths = [], t.children || (t.children = []), Object.defineProperty(t, "loaded", {
                    enumerable: !0,
                    get: function () {
                        return t.l
                    }
                }), Object.defineProperty(t, "id", {
                    enumerable: !0,
                    get: function () {
                        return t.i
                    }
                }), t.webpackPolyfill = 1), t
            }
        },
        function (t, e) {
            var r;
            r = function () {
                return this
            }();
            try {
                r = r || Function("return this")() || (0, eval)("this")
            } catch (t) {
                "object" == typeof window && (r = window)
            }
            t.exports = r
        },
        function (t, e, r) {
            (function (t, n) {
                var i;
                (function () {
                    var o, a = 200,
                        s = "Unsupported core-js use. Try https://npms.io/search?q=ponyfill.",
                        u = "Expected a function",
                        l = "__lodash_hash_undefined__",
                        h = 500,
                        c = "__lodash_placeholder__",
                        f = 1,
                        d = 2,
                        p = 4,
                        v = 1,
                        g = 2,
                        _ = 1,
                        y = 2,
                        m = 4,
                        x = 8,
                        b = 16,
                        w = 32,
                        S = 64,
                        k = 128,
                        T = 256,
                        C = 512,
                        A = 30,
                        L = "...",
                        P = 800,
                        M = 16,
                        B = 1,
                        D = 2,
                        z = 1 / 0,
                        O = 9007199254740991,
                        I = 1.7976931348623157e308,
                        R = NaN,
                        E = 4294967295,
                        j = E - 1,
                        N = E >>> 1,
                        F = [
                            ["ary", k],
                            ["bind", _],
                            ["bindKey", y],
                            ["curry", x],
                            ["curryRight", b],
                            ["flip", C],
                            ["partial", w],
                            ["partialRight", S],
                            ["rearg", T]
                        ],
                        G = "[object Arguments]",
                        W = "[object Array]",
                        H = "[object AsyncFunction]",
                        X = "[object Boolean]",
                        q = "[object Date]",
                        U = "[object DOMException]",
                        Y = "[object Error]",
                        V = "[object Function]",
                        $ = "[object GeneratorFunction]",
                        Z = "[object Map]",
                        Q = "[object Number]",
                        K = "[object Null]",
                        J = "[object Object]",
                        tt = "[object Proxy]",
                        et = "[object RegExp]",
                        rt = "[object Set]",
                        nt = "[object String]",
                        it = "[object Symbol]",
                        ot = "[object Undefined]",
                        at = "[object WeakMap]",
                        st = "[object WeakSet]",
                        ut = "[object ArrayBuffer]",
                        lt = "[object DataView]",
                        ht = "[object Float32Array]",
                        ct = "[object Float64Array]",
                        ft = "[object Int8Array]",
                        dt = "[object Int16Array]",
                        pt = "[object Int32Array]",
                        vt = "[object Uint8Array]",
                        gt = "[object Uint8ClampedArray]",
                        _t = "[object Uint16Array]",
                        yt = "[object Uint32Array]",
                        mt = /\b__p \+= '';/g,
                        xt = /\b(__p \+=) '' \+/g,
                        bt = /(__e\(.*?\)|\b__t\)) \+\n'';/g,
                        wt = /&(?:amp|lt|gt|quot|#39);/g,
                        St = /[&<>"']/g,
                        kt = RegExp(wt.source),
                        Tt = RegExp(St.source),
                        Ct = /<%-([\s\S]+?)%>/g,
                        At = /<%([\s\S]+?)%>/g,
                        Lt = /<%=([\s\S]+?)%>/g,
                        Pt = /\.|\[(?:[^[\]]*|(["'])(?:(?!\1)[^\\]|\\.)*?\1)\]/,
                        Mt = /^\w*$/,
                        Bt = /[^.[\]]+|\[(?:(-?\d+(?:\.\d+)?)|(["'])((?:(?!\2)[^\\]|\\.)*?)\2)\]|(?=(?:\.|\[\])(?:\.|\[\]|$))/g,
                        Dt = /[\\^$.*+?()[\]{}|]/g,
                        zt = RegExp(Dt.source),
                        Ot = /^\s+|\s+$/g,
                        It = /^\s+/,
                        Rt = /\s+$/,
                        Et = /\{(?:\n\/\* \[wrapped with .+\] \*\/)?\n?/,
                        jt = /\{\n\/\* \[wrapped with (.+)\] \*/,
                        Nt = /,? & /,
                        Ft = /[^\x00-\x2f\x3a-\x40\x5b-\x60\x7b-\x7f]+/g,
                        Gt = /\\(\\)?/g,
                        Wt = /\$\{([^\\}]*(?:\\.[^\\}]*)*)\}/g,
                        Ht = /\w*$/,
                        Xt = /^[-+]0x[0-9a-f]+$/i,
                        qt = /^0b[01]+$/i,
                        Ut = /^\[object .+?Constructor\]$/,
                        Yt = /^0o[0-7]+$/i,
                        Vt = /^(?:0|[1-9]\d*)$/,
                        $t = /[\xc0-\xd6\xd8-\xf6\xf8-\xff\u0100-\u017f]/g,
                        Zt = /($^)/,
                        Qt = /['\n\r\u2028\u2029\\]/g,
                        Kt = "\\u0300-\\u036f\\ufe20-\\ufe2f\\u20d0-\\u20ff",
                        Jt = "\\xac\\xb1\\xd7\\xf7\\x00-\\x2f\\x3a-\\x40\\x5b-\\x60\\x7b-\\xbf\\u2000-\\u206f \\t\\x0b\\f\\xa0\\ufeff\\n\\r\\u2028\\u2029\\u1680\\u180e\\u2000\\u2001\\u2002\\u2003\\u2004\\u2005\\u2006\\u2007\\u2008\\u2009\\u200a\\u202f\\u205f\\u3000",
                        te = "[\\ud800-\\udfff]",
                        ee = "[" + Jt + "]",
                        re = "[" + Kt + "]",
                        ne = "\\d+",
                        ie = "[\\u2700-\\u27bf]",
                        oe = "[a-z\\xdf-\\xf6\\xf8-\\xff]",
                        ae = "[^\\ud800-\\udfff" + Jt + ne + "\\u2700-\\u27bfa-z\\xdf-\\xf6\\xf8-\\xffA-Z\\xc0-\\xd6\\xd8-\\xde]",
                        se = "\\ud83c[\\udffb-\\udfff]",
                        ue = "[^\\ud800-\\udfff]",
                        le = "(?:\\ud83c[\\udde6-\\uddff]){2}",
                        he = "[\\ud800-\\udbff][\\udc00-\\udfff]",
                        ce = "[A-Z\\xc0-\\xd6\\xd8-\\xde]",
                        fe = "(?:" + oe + "|" + ae + ")",
                        de = "(?:" + ce + "|" + ae + ")",
                        pe = "(?:" + re + "|" + se + ")" + "?",
                        ve = "[\\ufe0e\\ufe0f]?" + pe + ("(?:\\u200d(?:" + [ue, le, he].join("|") + ")[\\ufe0e\\ufe0f]?" + pe + ")*"),
                        ge = "(?:" + [ie, le, he].join("|") + ")" + ve,
                        _e = "(?:" + [ue + re + "?", re, le, he, te].join("|") + ")",
                        ye = RegExp("['’]", "g"),
                        me = RegExp(re, "g"),
                        xe = RegExp(se + "(?=" + se + ")|" + _e + ve, "g"),
                        be = RegExp([ce + "?" + oe + "+(?:['’](?:d|ll|m|re|s|t|ve))?(?=" + [ee, ce, "$"].join("|") + ")", de + "+(?:['’](?:D|LL|M|RE|S|T|VE))?(?=" + [ee, ce + fe, "$"].join("|") + ")", ce + "?" + fe + "+(?:['’](?:d|ll|m|re|s|t|ve))?", ce + "+(?:['’](?:D|LL|M|RE|S|T|VE))?", "\\d*(?:1ST|2ND|3RD|(?![123])\\dTH)(?=\\b|[a-z_])", "\\d*(?:1st|2nd|3rd|(?![123])\\dth)(?=\\b|[A-Z_])", ne, ge].join("|"), "g"),
                        we = RegExp("[\\u200d\\ud800-\\udfff" + Kt + "\\ufe0e\\ufe0f]"),
                        Se = /[a-z][A-Z]|[A-Z]{2,}[a-z]|[0-9][a-zA-Z]|[a-zA-Z][0-9]|[^a-zA-Z0-9 ]/,
                        ke = ["Array", "Buffer", "DataView", "Date", "Error", "Float32Array", "Float64Array", "Function", "Int8Array", "Int16Array", "Int32Array", "Map", "Math", "Object", "Promise", "RegExp", "Set", "String", "Symbol", "TypeError", "Uint8Array", "Uint8ClampedArray", "Uint16Array", "Uint32Array", "WeakMap", "_", "clearTimeout", "isFinite", "parseInt", "setTimeout"],
                        Te = -1,
                        Ce = {};
                    Ce[ht] = Ce[ct] = Ce[ft] = Ce[dt] = Ce[pt] = Ce[vt] = Ce[gt] = Ce[_t] = Ce[yt] = !0, Ce[G] = Ce[W] = Ce[ut] = Ce[X] = Ce[lt] = Ce[q] = Ce[Y] = Ce[V] = Ce[Z] = Ce[Q] = Ce[J] = Ce[et] = Ce[rt] = Ce[nt] = Ce[at] = !1;
                    var Ae = {};
                    Ae[G] = Ae[W] = Ae[ut] = Ae[lt] = Ae[X] = Ae[q] = Ae[ht] = Ae[ct] = Ae[ft] = Ae[dt] = Ae[pt] = Ae[Z] = Ae[Q] = Ae[J] = Ae[et] = Ae[rt] = Ae[nt] = Ae[it] = Ae[vt] = Ae[gt] = Ae[_t] = Ae[yt] = !0, Ae[Y] = Ae[V] = Ae[at] = !1;
                    var Le = {
                            "\\": "\\",
                            "'": "'",
                            "\n": "n",
                            "\r": "r",
                            "\u2028": "u2028",
                            "\u2029": "u2029"
                        },
                        Pe = parseFloat,
                        Me = parseInt,
                        Be = "object" == typeof t && t && t.Object === Object && t,
                        De = "object" == typeof self && self && self.Object === Object && self,
                        ze = Be || De || Function("return this")(),
                        Oe = "object" == typeof e && e && !e.nodeType && e,
                        Ie = Oe && "object" == typeof n && n && !n.nodeType && n,
                        Re = Ie && Ie.exports === Oe,
                        Ee = Re && Be.process,
                        je = function () {
                            try {
                                return Ee && Ee.binding && Ee.binding("util")
                            } catch (t) {}
                        }(),
                        Ne = je && je.isArrayBuffer,
                        Fe = je && je.isDate,
                        Ge = je && je.isMap,
                        We = je && je.isRegExp,
                        He = je && je.isSet,
                        Xe = je && je.isTypedArray;

                    function qe(t, e, r) {
                        switch (r.length) {
                        case 0:
                            return t.call(e);
                        case 1:
                            return t.call(e, r[0]);
                        case 2:
                            return t.call(e, r[0], r[1]);
                        case 3:
                            return t.call(e, r[0], r[1], r[2])
                        }
                        return t.apply(e, r)
                    }

                    function Ue(t, e, r, n) {
                        for (var i = -1, o = null == t ? 0 : t.length; ++i < o;) {
                            var a = t[i];
                            e(n, a, r(a), t)
                        }
                        return n
                    }

                    function Ye(t, e) {
                        for (var r = -1, n = null == t ? 0 : t.length; ++r < n && !1 !== e(t[r], r, t););
                        return t
                    }

                    function Ve(t, e) {
                        for (var r = null == t ? 0 : t.length; r-- && !1 !== e(t[r], r, t););
                        return t
                    }

                    function $e(t, e) {
                        for (var r = -1, n = null == t ? 0 : t.length; ++r < n;)
                            if (!e(t[r], r, t)) return !1;
                        return !0
                    }

                    function Ze(t, e) {
                        for (var r = -1, n = null == t ? 0 : t.length, i = 0, o = []; ++r < n;) {
                            var a = t[r];
                            e(a, r, t) && (o[i++] = a)
                        }
                        return o
                    }

                    function Qe(t, e) {
                        return !!(null == t ? 0 : t.length) && sr(t, e, 0) > -1
                    }

                    function Ke(t, e, r) {
                        for (var n = -1, i = null == t ? 0 : t.length; ++n < i;)
                            if (r(e, t[n])) return !0;
                        return !1
                    }

                    function Je(t, e) {
                        for (var r = -1, n = null == t ? 0 : t.length, i = Array(n); ++r < n;) i[r] = e(t[r], r, t);
                        return i
                    }

                    function tr(t, e) {
                        for (var r = -1, n = e.length, i = t.length; ++r < n;) t[i + r] = e[r];
                        return t
                    }

                    function er(t, e, r, n) {
                        var i = -1,
                            o = null == t ? 0 : t.length;
                        for (n && o && (r = t[++i]); ++i < o;) r = e(r, t[i], i, t);
                        return r
                    }

                    function rr(t, e, r, n) {
                        var i = null == t ? 0 : t.length;
                        for (n && i && (r = t[--i]); i--;) r = e(r, t[i], i, t);
                        return r
                    }

                    function nr(t, e) {
                        for (var r = -1, n = null == t ? 0 : t.length; ++r < n;)
                            if (e(t[r], r, t)) return !0;
                        return !1
                    }
                    var ir = cr("length");

                    function or(t, e, r) {
                        var n;
                        return r(t, function (t, r, i) {
                            if (e(t, r, i)) return n = r, !1
                        }), n
                    }

                    function ar(t, e, r, n) {
                        for (var i = t.length, o = r + (n ? 1 : -1); n ? o-- : ++o < i;)
                            if (e(t[o], o, t)) return o;
                        return -1
                    }

                    function sr(t, e, r) {
                        return e == e ? function (t, e, r) {
                            var n = r - 1,
                                i = t.length;
                            for (; ++n < i;)
                                if (t[n] === e) return n;
                            return -1
                        }(t, e, r) : ar(t, lr, r)
                    }

                    function ur(t, e, r, n) {
                        for (var i = r - 1, o = t.length; ++i < o;)
                            if (n(t[i], e)) return i;
                        return -1
                    }

                    function lr(t) {
                        return t != t
                    }

                    function hr(t, e) {
                        var r = null == t ? 0 : t.length;
                        return r ? pr(t, e) / r : R
                    }

                    function cr(t) {
                        return function (e) {
                            return null == e ? o : e[t]
                        }
                    }

                    function fr(t) {
                        return function (e) {
                            return null == t ? o : t[e]
                        }
                    }

                    function dr(t, e, r, n, i) {
                        return i(t, function (t, i, o) {
                            r = n ? (n = !1, t) : e(r, t, i, o)
                        }), r
                    }

                    function pr(t, e) {
                        for (var r, n = -1, i = t.length; ++n < i;) {
                            var a = e(t[n]);
                            a !== o && (r = r === o ? a : r + a)
                        }
                        return r
                    }

                    function vr(t, e) {
                        for (var r = -1, n = Array(t); ++r < t;) n[r] = e(r);
                        return n
                    }

                    function gr(t) {
                        return function (e) {
                            return t(e)
                        }
                    }

                    function _r(t, e) {
                        return Je(e, function (e) {
                            return t[e]
                        })
                    }

                    function yr(t, e) {
                        return t.has(e)
                    }

                    function mr(t, e) {
                        for (var r = -1, n = t.length; ++r < n && sr(e, t[r], 0) > -1;);
                        return r
                    }

                    function xr(t, e) {
                        for (var r = t.length; r-- && sr(e, t[r], 0) > -1;);
                        return r
                    }
                    var br = fr({
                            "À": "A",
                            "Á": "A",
                            "Â": "A",
                            "Ã": "A",
                            "Ä": "A",
                            "Å": "A",
                            "à": "a",
                            "á": "a",
                            "â": "a",
                            "ã": "a",
                            "ä": "a",
                            "å": "a",
                            "Ç": "C",
                            "ç": "c",
                            "Ð": "D",
                            "ð": "d",
                            "È": "E",
                            "É": "E",
                            "Ê": "E",
                            "Ë": "E",
                            "è": "e",
                            "é": "e",
                            "ê": "e",
                            "ë": "e",
                            "Ì": "I",
                            "Í": "I",
                            "Î": "I",
                            "Ï": "I",
                            "ì": "i",
                            "í": "i",
                            "î": "i",
                            "ï": "i",
                            "Ñ": "N",
                            "ñ": "n",
                            "Ò": "O",
                            "Ó": "O",
                            "Ô": "O",
                            "Õ": "O",
                            "Ö": "O",
                            "Ø": "O",
                            "ò": "o",
                            "ó": "o",
                            "ô": "o",
                            "õ": "o",
                            "ö": "o",
                            "ø": "o",
                            "Ù": "U",
                            "Ú": "U",
                            "Û": "U",
                            "Ü": "U",
                            "ù": "u",
                            "ú": "u",
                            "û": "u",
                            "ü": "u",
                            "Ý": "Y",
                            "ý": "y",
                            "ÿ": "y",
                            "Æ": "Ae",
                            "æ": "ae",
                            "Þ": "Th",
                            "þ": "th",
                            "ß": "ss",
                            "Ā": "A",
                            "Ă": "A",
                            "Ą": "A",
                            "ā": "a",
                            "ă": "a",
                            "ą": "a",
                            "Ć": "C",
                            "Ĉ": "C",
                            "Ċ": "C",
                            "Č": "C",
                            "ć": "c",
                            "ĉ": "c",
                            "ċ": "c",
                            "č": "c",
                            "Ď": "D",
                            "Đ": "D",
                            "ď": "d",
                            "đ": "d",
                            "Ē": "E",
                            "Ĕ": "E",
                            "Ė": "E",
                            "Ę": "E",
                            "Ě": "E",
                            "ē": "e",
                            "ĕ": "e",
                            "ė": "e",
                            "ę": "e",
                            "ě": "e",
                            "Ĝ": "G",
                            "Ğ": "G",
                            "Ġ": "G",
                            "Ģ": "G",
                            "ĝ": "g",
                            "ğ": "g",
                            "ġ": "g",
                            "ģ": "g",
                            "Ĥ": "H",
                            "Ħ": "H",
                            "ĥ": "h",
                            "ħ": "h",
                            "Ĩ": "I",
                            "Ī": "I",
                            "Ĭ": "I",
                            "Į": "I",
                            "İ": "I",
                            "ĩ": "i",
                            "ī": "i",
                            "ĭ": "i",
                            "į": "i",
                            "ı": "i",
                            "Ĵ": "J",
                            "ĵ": "j",
                            "Ķ": "K",
                            "ķ": "k",
                            "ĸ": "k",
                            "Ĺ": "L",
                            "Ļ": "L",
                            "Ľ": "L",
                            "Ŀ": "L",
                            "Ł": "L",
                            "ĺ": "l",
                            "ļ": "l",
                            "ľ": "l",
                            "ŀ": "l",
                            "ł": "l",
                            "Ń": "N",
                            "Ņ": "N",
                            "Ň": "N",
                            "Ŋ": "N",
                            "ń": "n",
                            "ņ": "n",
                            "ň": "n",
                            "ŋ": "n",
                            "Ō": "O",
                            "Ŏ": "O",
                            "Ő": "O",
                            "ō": "o",
                            "ŏ": "o",
                            "ő": "o",
                            "Ŕ": "R",
                            "Ŗ": "R",
                            "Ř": "R",
                            "ŕ": "r",
                            "ŗ": "r",
                            "ř": "r",
                            "Ś": "S",
                            "Ŝ": "S",
                            "Ş": "S",
                            "Š": "S",
                            "ś": "s",
                            "ŝ": "s",
                            "ş": "s",
                            "š": "s",
                            "Ţ": "T",
                            "Ť": "T",
                            "Ŧ": "T",
                            "ţ": "t",
                            "ť": "t",
                            "ŧ": "t",
                            "Ũ": "U",
                            "Ū": "U",
                            "Ŭ": "U",
                            "Ů": "U",
                            "Ű": "U",
                            "Ų": "U",
                            "ũ": "u",
                            "ū": "u",
                            "ŭ": "u",
                            "ů": "u",
                            "ű": "u",
                            "ų": "u",
                            "Ŵ": "W",
                            "ŵ": "w",
                            "Ŷ": "Y",
                            "ŷ": "y",
                            "Ÿ": "Y",
                            "Ź": "Z",
                            "Ż": "Z",
                            "Ž": "Z",
                            "ź": "z",
                            "ż": "z",
                            "ž": "z",
                            "Ĳ": "IJ",
                            "ĳ": "ij",
                            "Œ": "Oe",
                            "œ": "oe",
                            "ŉ": "'n",
                            "ſ": "s"
                        }),
                        wr = fr({
                            "&": "&amp;",
                            "<": "&lt;",
                            ">": "&gt;",
                            '"': "&quot;",
                            "'": "&#39;"
                        });

                    function Sr(t) {
                        return "\\" + Le[t]
                    }

                    function kr(t) {
                        return we.test(t)
                    }

                    function Tr(t) {
                        var e = -1,
                            r = Array(t.size);
                        return t.forEach(function (t, n) {
                            r[++e] = [n, t]
                        }), r
                    }

                    function Cr(t, e) {
                        return function (r) {
                            return t(e(r))
                        }
                    }

                    function Ar(t, e) {
                        for (var r = -1, n = t.length, i = 0, o = []; ++r < n;) {
                            var a = t[r];
                            a !== e && a !== c || (t[r] = c, o[i++] = r)
                        }
                        return o
                    }

                    function Lr(t, e) {
                        return "__proto__" == e ? o : t[e]
                    }

                    function Pr(t) {
                        var e = -1,
                            r = Array(t.size);
                        return t.forEach(function (t) {
                            r[++e] = t
                        }), r
                    }

                    function Mr(t) {
                        var e = -1,
                            r = Array(t.size);
                        return t.forEach(function (t) {
                            r[++e] = [t, t]
                        }), r
                    }

                    function Br(t) {
                        return kr(t) ? function (t) {
                            var e = xe.lastIndex = 0;
                            for (; xe.test(t);)++e;
                            return e
                        }(t) : ir(t)
                    }

                    function Dr(t) {
                        return kr(t) ? function (t) {
                            return t.match(xe) || []
                        }(t) : function (t) {
                            return t.split("")
                        }(t)
                    }
                    var zr = fr({
                        "&amp;": "&",
                        "&lt;": "<",
                        "&gt;": ">",
                        "&quot;": '"',
                        "&#39;": "'"
                    });
                    var Or = function t(e) {
                        var r, n = (e = null == e ? ze : Or.defaults(ze.Object(), e, Or.pick(ze, ke))).Array,
                            i = e.Date,
                            Kt = e.Error,
                            Jt = e.Function,
                            te = e.Math,
                            ee = e.Object,
                            re = e.RegExp,
                            ne = e.String,
                            ie = e.TypeError,
                            oe = n.prototype,
                            ae = Jt.prototype,
                            se = ee.prototype,
                            ue = e["__core-js_shared__"],
                            le = ae.toString,
                            he = se.hasOwnProperty,
                            ce = 0,
                            fe = (r = /[^.]+$/.exec(ue && ue.keys && ue.keys.IE_PROTO || "")) ? "Symbol(src)_1." + r : "",
                            de = se.toString,
                            pe = le.call(ee),
                            ve = ze._,
                            ge = re("^" + le.call(he).replace(Dt, "\\$&").replace(/hasOwnProperty|(function).*?(?=\\\()| for .+?(?=\\\])/g, "$1.*?") + "$"),
                            _e = Re ? e.Buffer : o,
                            xe = e.Symbol,
                            we = e.Uint8Array,
                            Le = _e ? _e.allocUnsafe : o,
                            Be = Cr(ee.getPrototypeOf, ee),
                            De = ee.create,
                            Oe = se.propertyIsEnumerable,
                            Ie = oe.splice,
                            Ee = xe ? xe.isConcatSpreadable : o,
                            je = xe ? xe.iterator : o,
                            ir = xe ? xe.toStringTag : o,
                            fr = function () {
                                try {
                                    var t = Fo(ee, "defineProperty");
                                    return t({}, "", {}), t
                                } catch (t) {}
                            }(),
                            Ir = e.clearTimeout !== ze.clearTimeout && e.clearTimeout,
                            Rr = i && i.now !== ze.Date.now && i.now,
                            Er = e.setTimeout !== ze.setTimeout && e.setTimeout,
                            jr = te.ceil,
                            Nr = te.floor,
                            Fr = ee.getOwnPropertySymbols,
                            Gr = _e ? _e.isBuffer : o,
                            Wr = e.isFinite,
                            Hr = oe.join,
                            Xr = Cr(ee.keys, ee),
                            qr = te.max,
                            Ur = te.min,
                            Yr = i.now,
                            Vr = e.parseInt,
                            $r = te.random,
                            Zr = oe.reverse,
                            Qr = Fo(e, "DataView"),
                            Kr = Fo(e, "Map"),
                            Jr = Fo(e, "Promise"),
                            tn = Fo(e, "Set"),
                            en = Fo(e, "WeakMap"),
                            rn = Fo(ee, "create"),
                            nn = en && new en,
                            on = {},
                            an = ca(Qr),
                            sn = ca(Kr),
                            un = ca(Jr),
                            ln = ca(tn),
                            hn = ca(en),
                            cn = xe ? xe.prototype : o,
                            fn = cn ? cn.valueOf : o,
                            dn = cn ? cn.toString : o;

                        function pn(t) {
                            if (Ls(t) && !_s(t) && !(t instanceof yn)) {
                                if (t instanceof _n) return t;
                                if (he.call(t, "__wrapped__")) return fa(t)
                            }
                            return new _n(t)
                        }
                        var vn = function () {
                            function t() {}
                            return function (e) {
                                if (!As(e)) return {};
                                if (De) return De(e);
                                t.prototype = e;
                                var r = new t;
                                return t.prototype = o, r
                            }
                        }();

                        function gn() {}

                        function _n(t, e) {
                            this.__wrapped__ = t, this.__actions__ = [], this.__chain__ = !!e, this.__index__ = 0, this.__values__ = o
                        }

                        function yn(t) {
                            this.__wrapped__ = t, this.__actions__ = [], this.__dir__ = 1, this.__filtered__ = !1, this.__iteratees__ = [], this.__takeCount__ = E, this.__views__ = []
                        }

                        function mn(t) {
                            var e = -1,
                                r = null == t ? 0 : t.length;
                            for (this.clear(); ++e < r;) {
                                var n = t[e];
                                this.set(n[0], n[1])
                            }
                        }

                        function xn(t) {
                            var e = -1,
                                r = null == t ? 0 : t.length;
                            for (this.clear(); ++e < r;) {
                                var n = t[e];
                                this.set(n[0], n[1])
                            }
                        }

                        function bn(t) {
                            var e = -1,
                                r = null == t ? 0 : t.length;
                            for (this.clear(); ++e < r;) {
                                var n = t[e];
                                this.set(n[0], n[1])
                            }
                        }

                        function wn(t) {
                            var e = -1,
                                r = null == t ? 0 : t.length;
                            for (this.__data__ = new bn; ++e < r;) this.add(t[e])
                        }

                        function Sn(t) {
                            var e = this.__data__ = new xn(t);
                            this.size = e.size
                        }

                        function kn(t, e) {
                            var r = _s(t),
                                n = !r && gs(t),
                                i = !r && !n && bs(t),
                                o = !r && !n && !i && Rs(t),
                                a = r || n || i || o,
                                s = a ? vr(t.length, ne) : [],
                                u = s.length;
                            for (var l in t)!e && !he.call(t, l) || a && ("length" == l || i && ("offset" == l || "parent" == l) || o && ("buffer" == l || "byteLength" == l || "byteOffset" == l) || Yo(l, u)) || s.push(l);
                            return s
                        }

                        function Tn(t) {
                            var e = t.length;
                            return e ? t[wi(0, e - 1)] : o
                        }

                        function Cn(t, e) {
                            return ua(no(t), In(e, 0, t.length))
                        }

                        function An(t) {
                            return ua(no(t))
                        }

                        function Ln(t, e, r) {
                            (r === o || ds(t[e], r)) && (r !== o || e in t) || zn(t, e, r)
                        }

                        function Pn(t, e, r) {
                            var n = t[e];
                            he.call(t, e) && ds(n, r) && (r !== o || e in t) || zn(t, e, r)
                        }

                        function Mn(t, e) {
                            for (var r = t.length; r--;)
                                if (ds(t[r][0], e)) return r;
                            return -1
                        }

                        function Bn(t, e, r, n) {
                            return Fn(t, function (t, i, o) {
                                e(n, t, r(t), o)
                            }), n
                        }

                        function Dn(t, e) {
                            return t && io(e, iu(e), t)
                        }

                        function zn(t, e, r) {
                            "__proto__" == e && fr ? fr(t, e, {
                                configurable: !0,
                                enumerable: !0,
                                value: r,
                                writable: !0
                            }) : t[e] = r
                        }

                        function On(t, e) {
                            for (var r = -1, i = e.length, a = n(i), s = null == t; ++r < i;) a[r] = s ? o : Js(t, e[r]);
                            return a
                        }

                        function In(t, e, r) {
                            return t == t && (r !== o && (t = t <= r ? t : r), e !== o && (t = t >= e ? t : e)), t
                        }

                        function Rn(t, e, r, n, i, a) {
                            var s, u = e & f,
                                l = e & d,
                                h = e & p;
                            if (r && (s = i ? r(t, n, i, a) : r(t)), s !== o) return s;
                            if (!As(t)) return t;
                            var c = _s(t);
                            if (c) {
                                if (s = function (t) {
                                    var e = t.length,
                                        r = new t.constructor(e);
                                    return e && "string" == typeof t[0] && he.call(t, "index") && (r.index = t.index, r.input = t.input), r
                                }(t), !u) return no(t, s)
                            } else {
                                var v = Ho(t),
                                    g = v == V || v == $;
                                if (bs(t)) return Qi(t, u);
                                if (v == J || v == G || g && !i) {
                                    if (s = l || g ? {} : qo(t), !u) return l ? function (t, e) {
                                        return io(t, Wo(t), e)
                                    }(t, function (t, e) {
                                        return t && io(e, ou(e), t)
                                    }(s, t)) : function (t, e) {
                                        return io(t, Go(t), e)
                                    }(t, Dn(s, t))
                                } else {
                                    if (!Ae[v]) return i ? t : {};
                                    s = function (t, e, r) {
                                        var n, i, o, a = t.constructor;
                                        switch (e) {
                                        case ut:
                                            return Ki(t);
                                        case X:
                                        case q:
                                            return new a(+t);
                                        case lt:
                                            return function (t, e) {
                                                var r = e ? Ki(t.buffer) : t.buffer;
                                                return new t.constructor(r, t.byteOffset, t.byteLength)
                                            }(t, r);
                                        case ht:
                                        case ct:
                                        case ft:
                                        case dt:
                                        case pt:
                                        case vt:
                                        case gt:
                                        case _t:
                                        case yt:
                                            return Ji(t, r);
                                        case Z:
                                            return new a;
                                        case Q:
                                        case nt:
                                            return new a(t);
                                        case et:
                                            return (o = new(i = t).constructor(i.source, Ht.exec(i))).lastIndex = i.lastIndex, o;
                                        case rt:
                                            return new a;
                                        case it:
                                            return n = t, fn ? ee(fn.call(n)) : {}
                                        }
                                    }(t, v, u)
                                }
                            }
                            a || (a = new Sn);
                            var _ = a.get(t);
                            if (_) return _;
                            if (a.set(t, s), zs(t)) return t.forEach(function (n) {
                                s.add(Rn(n, e, r, n, t, a))
                            }), s;
                            if (Ps(t)) return t.forEach(function (n, i) {
                                s.set(i, Rn(n, e, r, i, t, a))
                            }), s;
                            var y = c ? o : (h ? l ? zo : Do : l ? ou : iu)(t);
                            return Ye(y || t, function (n, i) {
                                y && (n = t[i = n]), Pn(s, i, Rn(n, e, r, i, t, a))
                            }), s
                        }

                        function En(t, e, r) {
                            var n = r.length;
                            if (null == t) return !n;
                            for (t = ee(t); n--;) {
                                var i = r[n],
                                    a = e[i],
                                    s = t[i];
                                if (s === o && !(i in t) || !a(s)) return !1
                            }
                            return !0
                        }

                        function jn(t, e, r) {
                            if ("function" != typeof t) throw new ie(u);
                            return ia(function () {
                                t.apply(o, r)
                            }, e)
                        }

                        function Nn(t, e, r, n) {
                            var i = -1,
                                o = Qe,
                                s = !0,
                                u = t.length,
                                l = [],
                                h = e.length;
                            if (!u) return l;
                            r && (e = Je(e, gr(r))), n ? (o = Ke, s = !1) : e.length >= a && (o = yr, s = !1, e = new wn(e));
                            t: for (; ++i < u;) {
                                var c = t[i],
                                    f = null == r ? c : r(c);
                                if (c = n || 0 !== c ? c : 0, s && f == f) {
                                    for (var d = h; d--;)
                                        if (e[d] === f) continue t;
                                    l.push(c)
                                } else o(e, f, n) || l.push(c)
                            }
                            return l
                        }
                        pn.templateSettings = {
                            escape: Ct,
                            evaluate: At,
                            interpolate: Lt,
                            variable: "",
                            imports: {
                                _: pn
                            }
                        }, pn.prototype = gn.prototype, pn.prototype.constructor = pn, _n.prototype = vn(gn.prototype), _n.prototype.constructor = _n, yn.prototype = vn(gn.prototype), yn.prototype.constructor = yn, mn.prototype.clear = function () {
                            this.__data__ = rn ? rn(null) : {}, this.size = 0
                        }, mn.prototype.delete = function (t) {
                            var e = this.has(t) && delete this.__data__[t];
                            return this.size -= e ? 1 : 0, e
                        }, mn.prototype.get = function (t) {
                            var e = this.__data__;
                            if (rn) {
                                var r = e[t];
                                return r === l ? o : r
                            }
                            return he.call(e, t) ? e[t] : o
                        }, mn.prototype.has = function (t) {
                            var e = this.__data__;
                            return rn ? e[t] !== o : he.call(e, t)
                        }, mn.prototype.set = function (t, e) {
                            var r = this.__data__;
                            return this.size += this.has(t) ? 0 : 1, r[t] = rn && e === o ? l : e, this
                        }, xn.prototype.clear = function () {
                            this.__data__ = [], this.size = 0
                        }, xn.prototype.delete = function (t) {
                            var e = this.__data__,
                                r = Mn(e, t);
                            return !(r < 0 || (r == e.length - 1 ? e.pop() : Ie.call(e, r, 1), --this.size, 0))
                        }, xn.prototype.get = function (t) {
                            var e = this.__data__,
                                r = Mn(e, t);
                            return r < 0 ? o : e[r][1]
                        }, xn.prototype.has = function (t) {
                            return Mn(this.__data__, t) > -1
                        }, xn.prototype.set = function (t, e) {
                            var r = this.__data__,
                                n = Mn(r, t);
                            return n < 0 ? (++this.size, r.push([t, e])) : r[n][1] = e, this
                        }, bn.prototype.clear = function () {
                            this.size = 0, this.__data__ = {
                                hash: new mn,
                                map: new(Kr || xn),
                                string: new mn
                            }
                        }, bn.prototype.delete = function (t) {
                            var e = jo(this, t).delete(t);
                            return this.size -= e ? 1 : 0, e
                        }, bn.prototype.get = function (t) {
                            return jo(this, t).get(t)
                        }, bn.prototype.has = function (t) {
                            return jo(this, t).has(t)
                        }, bn.prototype.set = function (t, e) {
                            var r = jo(this, t),
                                n = r.size;
                            return r.set(t, e), this.size += r.size == n ? 0 : 1, this
                        }, wn.prototype.add = wn.prototype.push = function (t) {
                            return this.__data__.set(t, l), this
                        }, wn.prototype.has = function (t) {
                            return this.__data__.has(t)
                        }, Sn.prototype.clear = function () {
                            this.__data__ = new xn, this.size = 0
                        }, Sn.prototype.delete = function (t) {
                            var e = this.__data__,
                                r = e.delete(t);
                            return this.size = e.size, r
                        }, Sn.prototype.get = function (t) {
                            return this.__data__.get(t)
                        }, Sn.prototype.has = function (t) {
                            return this.__data__.has(t)
                        }, Sn.prototype.set = function (t, e) {
                            var r = this.__data__;
                            if (r instanceof xn) {
                                var n = r.__data__;
                                if (!Kr || n.length < a - 1) return n.push([t, e]), this.size = ++r.size, this;
                                r = this.__data__ = new bn(n)
                            }
                            return r.set(t, e), this.size = r.size, this
                        };
                        var Fn = so(Vn),
                            Gn = so($n, !0);

                        function Wn(t, e) {
                            var r = !0;
                            return Fn(t, function (t, n, i) {
                                return r = !!e(t, n, i)
                            }), r
                        }

                        function Hn(t, e, r) {
                            for (var n = -1, i = t.length; ++n < i;) {
                                var a = t[n],
                                    s = e(a);
                                if (null != s && (u === o ? s == s && !Is(s) : r(s, u))) var u = s,
                                    l = a
                            }
                            return l
                        }

                        function Xn(t, e) {
                            var r = [];
                            return Fn(t, function (t, n, i) {
                                e(t, n, i) && r.push(t)
                            }), r
                        }

                        function qn(t, e, r, n, i) {
                            var o = -1,
                                a = t.length;
                            for (r || (r = Uo), i || (i = []); ++o < a;) {
                                var s = t[o];
                                e > 0 && r(s) ? e > 1 ? qn(s, e - 1, r, n, i) : tr(i, s) : n || (i[i.length] = s)
                            }
                            return i
                        }
                        var Un = uo(),
                            Yn = uo(!0);

                        function Vn(t, e) {
                            return t && Un(t, e, iu)
                        }

                        function $n(t, e) {
                            return t && Yn(t, e, iu)
                        }

                        function Zn(t, e) {
                            return Ze(e, function (e) {
                                return ks(t[e])
                            })
                        }

                        function Qn(t, e) {
                            for (var r = 0, n = (e = Yi(e, t)).length; null != t && r < n;) t = t[ha(e[r++])];
                            return r && r == n ? t : o
                        }

                        function Kn(t, e, r) {
                            var n = e(t);
                            return _s(t) ? n : tr(n, r(t))
                        }

                        function Jn(t) {
                            return null == t ? t === o ? ot : K : ir && ir in ee(t) ? function (t) {
                                var e = he.call(t, ir),
                                    r = t[ir];
                                try {
                                    t[ir] = o;
                                    var n = !0
                                } catch (t) {}
                                var i = de.call(t);
                                return n && (e ? t[ir] = r : delete t[ir]), i
                            }(t) : function (t) {
                                return de.call(t)
                            }(t)
                        }

                        function ti(t, e) {
                            return t > e
                        }

                        function ei(t, e) {
                            return null != t && he.call(t, e)
                        }

                        function ri(t, e) {
                            return null != t && e in ee(t)
                        }

                        function ni(t, e, r) {
                            for (var i = r ? Ke : Qe, a = t[0].length, s = t.length, u = s, l = n(s), h = 1 / 0, c = []; u--;) {
                                var f = t[u];
                                u && e && (f = Je(f, gr(e))), h = Ur(f.length, h), l[u] = !r && (e || a >= 120 && f.length >= 120) ? new wn(u && f) : o
                            }
                            f = t[0];
                            var d = -1,
                                p = l[0];
                            t: for (; ++d < a && c.length < h;) {
                                var v = f[d],
                                    g = e ? e(v) : v;
                                if (v = r || 0 !== v ? v : 0, !(p ? yr(p, g) : i(c, g, r))) {
                                    for (u = s; --u;) {
                                        var _ = l[u];
                                        if (!(_ ? yr(_, g) : i(t[u], g, r))) continue t
                                    }
                                    p && p.push(g), c.push(v)
                                }
                            }
                            return c
                        }

                        function ii(t, e, r) {
                            var n = null == (t = ra(t, e = Yi(e, t))) ? t : t[ha(Sa(e))];
                            return null == n ? o : qe(n, t, r)
                        }

                        function oi(t) {
                            return Ls(t) && Jn(t) == G
                        }

                        function ai(t, e, r, n, i) {
                            return t === e || (null == t || null == e || !Ls(t) && !Ls(e) ? t != t && e != e : function (t, e, r, n, i, a) {
                                var s = _s(t),
                                    u = _s(e),
                                    l = s ? W : Ho(t),
                                    h = u ? W : Ho(e),
                                    c = (l = l == G ? J : l) == J,
                                    f = (h = h == G ? J : h) == J,
                                    d = l == h;
                                if (d && bs(t)) {
                                    if (!bs(e)) return !1;
                                    s = !0, c = !1
                                }
                                if (d && !c) return a || (a = new Sn), s || Rs(t) ? Mo(t, e, r, n, i, a) : function (t, e, r, n, i, o, a) {
                                    switch (r) {
                                    case lt:
                                        if (t.byteLength != e.byteLength || t.byteOffset != e.byteOffset) return !1;
                                        t = t.buffer, e = e.buffer;
                                    case ut:
                                        return !(t.byteLength != e.byteLength || !o(new we(t), new we(e)));
                                    case X:
                                    case q:
                                    case Q:
                                        return ds(+t, +e);
                                    case Y:
                                        return t.name == e.name && t.message == e.message;
                                    case et:
                                    case nt:
                                        return t == e + "";
                                    case Z:
                                        var s = Tr;
                                    case rt:
                                        var u = n & v;
                                        if (s || (s = Pr), t.size != e.size && !u) return !1;
                                        var l = a.get(t);
                                        if (l) return l == e;
                                        n |= g, a.set(t, e);
                                        var h = Mo(s(t), s(e), n, i, o, a);
                                        return a.delete(t), h;
                                    case it:
                                        if (fn) return fn.call(t) == fn.call(e)
                                    }
                                    return !1
                                }(t, e, l, r, n, i, a);
                                if (!(r & v)) {
                                    var p = c && he.call(t, "__wrapped__"),
                                        _ = f && he.call(e, "__wrapped__");
                                    if (p || _) {
                                        var y = p ? t.value() : t,
                                            m = _ ? e.value() : e;
                                        return a || (a = new Sn), i(y, m, r, n, a)
                                    }
                                }
                                return !!d && (a || (a = new Sn), function (t, e, r, n, i, a) {
                                    var s = r & v,
                                        u = Do(t),
                                        l = u.length,
                                        h = Do(e).length;
                                    if (l != h && !s) return !1;
                                    for (var c = l; c--;) {
                                        var f = u[c];
                                        if (!(s ? f in e : he.call(e, f))) return !1
                                    }
                                    var d = a.get(t);
                                    if (d && a.get(e)) return d == e;
                                    var p = !0;
                                    a.set(t, e), a.set(e, t);
                                    for (var g = s; ++c < l;) {
                                        f = u[c];
                                        var _ = t[f],
                                            y = e[f];
                                        if (n) var m = s ? n(y, _, f, e, t, a) : n(_, y, f, t, e, a);
                                        if (!(m === o ? _ === y || i(_, y, r, n, a) : m)) {
                                            p = !1;
                                            break
                                        }
                                        g || (g = "constructor" == f)
                                    }
                                    if (p && !g) {
                                        var x = t.constructor,
                                            b = e.constructor;
                                        x != b && "constructor" in t && "constructor" in e && !("function" == typeof x && x instanceof x && "function" == typeof b && b instanceof b) && (p = !1)
                                    }
                                    return a.delete(t), a.delete(e), p
                                }(t, e, r, n, i, a))
                            }(t, e, r, n, ai, i))
                        }

                        function si(t, e, r, n) {
                            var i = r.length,
                                a = i,
                                s = !n;
                            if (null == t) return !a;
                            for (t = ee(t); i--;) {
                                var u = r[i];
                                if (s && u[2] ? u[1] !== t[u[0]] : !(u[0] in t)) return !1
                            }
                            for (; ++i < a;) {
                                var l = (u = r[i])[0],
                                    h = t[l],
                                    c = u[1];
                                if (s && u[2]) {
                                    if (h === o && !(l in t)) return !1
                                } else {
                                    var f = new Sn;
                                    if (n) var d = n(h, c, l, t, e, f);
                                    if (!(d === o ? ai(c, h, v | g, n, f) : d)) return !1
                                }
                            }
                            return !0
                        }

                        function ui(t) {
                            return !(!As(t) || fe && fe in t) && (ks(t) ? ge : Ut).test(ca(t))
                        }

                        function li(t) {
                            return "function" == typeof t ? t : null == t ? Mu : "object" == typeof t ? _s(t) ? vi(t[0], t[1]) : pi(t) : Nu(t)
                        }

                        function hi(t) {
                            if (!Ko(t)) return Xr(t);
                            var e = [];
                            for (var r in ee(t)) he.call(t, r) && "constructor" != r && e.push(r);
                            return e
                        }

                        function ci(t) {
                            if (!As(t)) return function (t) {
                                var e = [];
                                if (null != t)
                                    for (var r in ee(t)) e.push(r);
                                return e
                            }(t);
                            var e = Ko(t),
                                r = [];
                            for (var n in t)("constructor" != n || !e && he.call(t, n)) && r.push(n);
                            return r
                        }

                        function fi(t, e) {
                            return t < e
                        }

                        function di(t, e) {
                            var r = -1,
                                i = ms(t) ? n(t.length) : [];
                            return Fn(t, function (t, n, o) {
                                i[++r] = e(t, n, o)
                            }), i
                        }

                        function pi(t) {
                            var e = No(t);
                            return 1 == e.length && e[0][2] ? ta(e[0][0], e[0][1]) : function (r) {
                                return r === t || si(r, t, e)
                            }
                        }

                        function vi(t, e) {
                            return $o(t) && Jo(e) ? ta(ha(t), e) : function (r) {
                                var n = Js(r, t);
                                return n === o && n === e ? tu(r, t) : ai(e, n, v | g)
                            }
                        }

                        function gi(t, e, r, n, i) {
                            t !== e && Un(e, function (a, s) {
                                if (As(a)) i || (i = new Sn),
                                    function (t, e, r, n, i, a, s) {
                                        var u = Lr(t, r),
                                            l = Lr(e, r),
                                            h = s.get(l);
                                        if (h) Ln(t, r, h);
                                        else {
                                            var c = a ? a(u, l, r + "", t, e, s) : o,
                                                f = c === o;
                                            if (f) {
                                                var d = _s(l),
                                                    p = !d && bs(l),
                                                    v = !d && !p && Rs(l);
                                                c = l, d || p || v ? _s(u) ? c = u : xs(u) ? c = no(u) : p ? (f = !1, c = Qi(l, !0)) : v ? (f = !1, c = Ji(l, !0)) : c = [] : Bs(l) || gs(l) ? (c = u, gs(u) ? c = Xs(u) : (!As(u) || n && ks(u)) && (c = qo(l))) : f = !1
                                            }
                                            f && (s.set(l, c), i(c, l, n, a, s), s.delete(l)), Ln(t, r, c)
                                        }
                                    }(t, e, s, r, gi, n, i);
                                else {
                                    var u = n ? n(Lr(t, s), a, s + "", t, e, i) : o;
                                    u === o && (u = a), Ln(t, s, u)
                                }
                            }, ou)
                        }

                        function _i(t, e) {
                            var r = t.length;
                            if (r) return Yo(e += e < 0 ? r : 0, r) ? t[e] : o
                        }

                        function yi(t, e, r) {
                            var n = -1;
                            return e = Je(e.length ? e : [Mu], gr(Eo())),
                                function (t, e) {
                                    var r = t.length;
                                    for (t.sort(e); r--;) t[r] = t[r].value;
                                    return t
                                }(di(t, function (t, r, i) {
                                    return {
                                        criteria: Je(e, function (e) {
                                            return e(t)
                                        }),
                                        index: ++n,
                                        value: t
                                    }
                                }), function (t, e) {
                                    return function (t, e, r) {
                                        for (var n = -1, i = t.criteria, o = e.criteria, a = i.length, s = r.length; ++n < a;) {
                                            var u = to(i[n], o[n]);
                                            if (u) {
                                                if (n >= s) return u;
                                                var l = r[n];
                                                return u * ("desc" == l ? -1 : 1)
                                            }
                                        }
                                        return t.index - e.index
                                    }(t, e, r)
                                })
                        }

                        function mi(t, e, r) {
                            for (var n = -1, i = e.length, o = {}; ++n < i;) {
                                var a = e[n],
                                    s = Qn(t, a);
                                r(s, a) && Ai(o, Yi(a, t), s)
                            }
                            return o
                        }

                        function xi(t, e, r, n) {
                            var i = n ? ur : sr,
                                o = -1,
                                a = e.length,
                                s = t;
                            for (t === e && (e = no(e)), r && (s = Je(t, gr(r))); ++o < a;)
                                for (var u = 0, l = e[o], h = r ? r(l) : l;
                                    (u = i(s, h, u, n)) > -1;) s !== t && Ie.call(s, u, 1), Ie.call(t, u, 1);
                            return t
                        }

                        function bi(t, e) {
                            for (var r = t ? e.length : 0, n = r - 1; r--;) {
                                var i = e[r];
                                if (r == n || i !== o) {
                                    var o = i;
                                    Yo(i) ? Ie.call(t, i, 1) : Ni(t, i)
                                }
                            }
                            return t
                        }

                        function wi(t, e) {
                            return t + Nr($r() * (e - t + 1))
                        }

                        function Si(t, e) {
                            var r = "";
                            if (!t || e < 1 || e > O) return r;
                            do {
                                e % 2 && (r += t), (e = Nr(e / 2)) && (t += t)
                            } while (e);
                            return r
                        }

                        function ki(t, e) {
                            return oa(ea(t, e, Mu), t + "")
                        }

                        function Ti(t) {
                            return Tn(du(t))
                        }

                        function Ci(t, e) {
                            var r = du(t);
                            return ua(r, In(e, 0, r.length))
                        }

                        function Ai(t, e, r, n) {
                            if (!As(t)) return t;
                            for (var i = -1, a = (e = Yi(e, t)).length, s = a - 1, u = t; null != u && ++i < a;) {
                                var l = ha(e[i]),
                                    h = r;
                                if (i != s) {
                                    var c = u[l];
                                    (h = n ? n(c, l, u) : o) === o && (h = As(c) ? c : Yo(e[i + 1]) ? [] : {})
                                }
                                Pn(u, l, h), u = u[l]
                            }
                            return t
                        }
                        var Li = nn ? function (t, e) {
                                return nn.set(t, e), t
                            } : Mu,
                            Pi = fr ? function (t, e) {
                                return fr(t, "toString", {
                                    configurable: !0,
                                    enumerable: !1,
                                    value: Au(e),
                                    writable: !0
                                })
                            } : Mu;

                        function Mi(t) {
                            return ua(du(t))
                        }

                        function Bi(t, e, r) {
                            var i = -1,
                                o = t.length;
                            e < 0 && (e = -e > o ? 0 : o + e), (r = r > o ? o : r) < 0 && (r += o), o = e > r ? 0 : r - e >>> 0, e >>>= 0;
                            for (var a = n(o); ++i < o;) a[i] = t[i + e];
                            return a
                        }

                        function Di(t, e) {
                            var r;
                            return Fn(t, function (t, n, i) {
                                return !(r = e(t, n, i))
                            }), !!r
                        }

                        function zi(t, e, r) {
                            var n = 0,
                                i = null == t ? n : t.length;
                            if ("number" == typeof e && e == e && i <= N) {
                                for (; n < i;) {
                                    var o = n + i >>> 1,
                                        a = t[o];
                                    null !== a && !Is(a) && (r ? a <= e : a < e) ? n = o + 1 : i = o
                                }
                                return i
                            }
                            return Oi(t, e, Mu, r)
                        }

                        function Oi(t, e, r, n) {
                            e = r(e);
                            for (var i = 0, a = null == t ? 0 : t.length, s = e != e, u = null === e, l = Is(e), h = e === o; i < a;) {
                                var c = Nr((i + a) / 2),
                                    f = r(t[c]),
                                    d = f !== o,
                                    p = null === f,
                                    v = f == f,
                                    g = Is(f);
                                if (s) var _ = n || v;
                                else _ = h ? v && (n || d) : u ? v && d && (n || !p) : l ? v && d && !p && (n || !g) : !p && !g && (n ? f <= e : f < e);
                                _ ? i = c + 1 : a = c
                            }
                            return Ur(a, j)
                        }

                        function Ii(t, e) {
                            for (var r = -1, n = t.length, i = 0, o = []; ++r < n;) {
                                var a = t[r],
                                    s = e ? e(a) : a;
                                if (!r || !ds(s, u)) {
                                    var u = s;
                                    o[i++] = 0 === a ? 0 : a
                                }
                            }
                            return o
                        }

                        function Ri(t) {
                            return "number" == typeof t ? t : Is(t) ? R : +t
                        }

                        function Ei(t) {
                            if ("string" == typeof t) return t;
                            if (_s(t)) return Je(t, Ei) + "";
                            if (Is(t)) return dn ? dn.call(t) : "";
                            var e = t + "";
                            return "0" == e && 1 / t == -z ? "-0" : e
                        }

                        function ji(t, e, r) {
                            var n = -1,
                                i = Qe,
                                o = t.length,
                                s = !0,
                                u = [],
                                l = u;
                            if (r) s = !1, i = Ke;
                            else if (o >= a) {
                                var h = e ? null : ko(t);
                                if (h) return Pr(h);
                                s = !1, i = yr, l = new wn
                            } else l = e ? [] : u;
                            t: for (; ++n < o;) {
                                var c = t[n],
                                    f = e ? e(c) : c;
                                if (c = r || 0 !== c ? c : 0, s && f == f) {
                                    for (var d = l.length; d--;)
                                        if (l[d] === f) continue t;
                                    e && l.push(f), u.push(c)
                                } else i(l, f, r) || (l !== u && l.push(f), u.push(c))
                            }
                            return u
                        }

                        function Ni(t, e) {
                            return null == (t = ra(t, e = Yi(e, t))) || delete t[ha(Sa(e))]
                        }

                        function Fi(t, e, r, n) {
                            return Ai(t, e, r(Qn(t, e)), n)
                        }

                        function Gi(t, e, r, n) {
                            for (var i = t.length, o = n ? i : -1;
                                (n ? o-- : ++o < i) && e(t[o], o, t););
                            return r ? Bi(t, n ? 0 : o, n ? o + 1 : i) : Bi(t, n ? o + 1 : 0, n ? i : o)
                        }

                        function Wi(t, e) {
                            var r = t;
                            return r instanceof yn && (r = r.value()), er(e, function (t, e) {
                                return e.func.apply(e.thisArg, tr([t], e.args))
                            }, r)
                        }

                        function Hi(t, e, r) {
                            var i = t.length;
                            if (i < 2) return i ? ji(t[0]) : [];
                            for (var o = -1, a = n(i); ++o < i;)
                                for (var s = t[o], u = -1; ++u < i;) u != o && (a[o] = Nn(a[o] || s, t[u], e, r));
                            return ji(qn(a, 1), e, r)
                        }

                        function Xi(t, e, r) {
                            for (var n = -1, i = t.length, a = e.length, s = {}; ++n < i;) {
                                var u = n < a ? e[n] : o;
                                r(s, t[n], u)
                            }
                            return s
                        }

                        function qi(t) {
                            return xs(t) ? t : []
                        }

                        function Ui(t) {
                            return "function" == typeof t ? t : Mu
                        }

                        function Yi(t, e) {
                            return _s(t) ? t : $o(t, e) ? [t] : la(qs(t))
                        }
                        var Vi = ki;

                        function $i(t, e, r) {
                            var n = t.length;
                            return r = r === o ? n : r, !e && r >= n ? t : Bi(t, e, r)
                        }
                        var Zi = Ir || function (t) {
                            return ze.clearTimeout(t)
                        };

                        function Qi(t, e) {
                            if (e) return t.slice();
                            var r = t.length,
                                n = Le ? Le(r) : new t.constructor(r);
                            return t.copy(n), n
                        }

                        function Ki(t) {
                            var e = new t.constructor(t.byteLength);
                            return new we(e).set(new we(t)), e
                        }

                        function Ji(t, e) {
                            var r = e ? Ki(t.buffer) : t.buffer;
                            return new t.constructor(r, t.byteOffset, t.length)
                        }

                        function to(t, e) {
                            if (t !== e) {
                                var r = t !== o,
                                    n = null === t,
                                    i = t == t,
                                    a = Is(t),
                                    s = e !== o,
                                    u = null === e,
                                    l = e == e,
                                    h = Is(e);
                                if (!u && !h && !a && t > e || a && s && l && !u && !h || n && s && l || !r && l || !i) return 1;
                                if (!n && !a && !h && t < e || h && r && i && !n && !a || u && r && i || !s && i || !l) return -1
                            }
                            return 0
                        }

                        function eo(t, e, r, i) {
                            for (var o = -1, a = t.length, s = r.length, u = -1, l = e.length, h = qr(a - s, 0), c = n(l + h), f = !i; ++u < l;) c[u] = e[u];
                            for (; ++o < s;)(f || o < a) && (c[r[o]] = t[o]);
                            for (; h--;) c[u++] = t[o++];
                            return c
                        }

                        function ro(t, e, r, i) {
                            for (var o = -1, a = t.length, s = -1, u = r.length, l = -1, h = e.length, c = qr(a - u, 0), f = n(c + h), d = !i; ++o < c;) f[o] = t[o];
                            for (var p = o; ++l < h;) f[p + l] = e[l];
                            for (; ++s < u;)(d || o < a) && (f[p + r[s]] = t[o++]);
                            return f
                        }

                        function no(t, e) {
                            var r = -1,
                                i = t.length;
                            for (e || (e = n(i)); ++r < i;) e[r] = t[r];
                            return e
                        }

                        function io(t, e, r, n) {
                            var i = !r;
                            r || (r = {});
                            for (var a = -1, s = e.length; ++a < s;) {
                                var u = e[a],
                                    l = n ? n(r[u], t[u], u, r, t) : o;
                                l === o && (l = t[u]), i ? zn(r, u, l) : Pn(r, u, l)
                            }
                            return r
                        }

                        function oo(t, e) {
                            return function (r, n) {
                                var i = _s(r) ? Ue : Bn,
                                    o = e ? e() : {};
                                return i(r, t, Eo(n, 2), o)
                            }
                        }

                        function ao(t) {
                            return ki(function (e, r) {
                                var n = -1,
                                    i = r.length,
                                    a = i > 1 ? r[i - 1] : o,
                                    s = i > 2 ? r[2] : o;
                                for (a = t.length > 3 && "function" == typeof a ? (i--, a) : o, s && Vo(r[0], r[1], s) && (a = i < 3 ? o : a, i = 1), e = ee(e); ++n < i;) {
                                    var u = r[n];
                                    u && t(e, u, n, a)
                                }
                                return e
                            })
                        }

                        function so(t, e) {
                            return function (r, n) {
                                if (null == r) return r;
                                if (!ms(r)) return t(r, n);
                                for (var i = r.length, o = e ? i : -1, a = ee(r);
                                    (e ? o-- : ++o < i) && !1 !== n(a[o], o, a););
                                return r
                            }
                        }

                        function uo(t) {
                            return function (e, r, n) {
                                for (var i = -1, o = ee(e), a = n(e), s = a.length; s--;) {
                                    var u = a[t ? s : ++i];
                                    if (!1 === r(o[u], u, o)) break
                                }
                                return e
                            }
                        }

                        function lo(t) {
                            return function (e) {
                                var r = kr(e = qs(e)) ? Dr(e) : o,
                                    n = r ? r[0] : e.charAt(0),
                                    i = r ? $i(r, 1).join("") : e.slice(1);
                                return n[t]() + i
                            }
                        }

                        function ho(t) {
                            return function (e) {
                                return er(ku(gu(e).replace(ye, "")), t, "")
                            }
                        }

                        function co(t) {
                            return function () {
                                var e = arguments;
                                switch (e.length) {
                                case 0:
                                    return new t;
                                case 1:
                                    return new t(e[0]);
                                case 2:
                                    return new t(e[0], e[1]);
                                case 3:
                                    return new t(e[0], e[1], e[2]);
                                case 4:
                                    return new t(e[0], e[1], e[2], e[3]);
                                case 5:
                                    return new t(e[0], e[1], e[2], e[3], e[4]);
                                case 6:
                                    return new t(e[0], e[1], e[2], e[3], e[4], e[5]);
                                case 7:
                                    return new t(e[0], e[1], e[2], e[3], e[4], e[5], e[6])
                                }
                                var r = vn(t.prototype),
                                    n = t.apply(r, e);
                                return As(n) ? n : r
                            }
                        }

                        function fo(t) {
                            return function (e, r, n) {
                                var i = ee(e);
                                if (!ms(e)) {
                                    var a = Eo(r, 3);
                                    e = iu(e), r = function (t) {
                                        return a(i[t], t, i)
                                    }
                                }
                                var s = t(e, r, n);
                                return s > -1 ? i[a ? e[s] : s] : o
                            }
                        }

                        function po(t) {
                            return Bo(function (e) {
                                var r = e.length,
                                    n = r,
                                    i = _n.prototype.thru;
                                for (t && e.reverse(); n--;) {
                                    var a = e[n];
                                    if ("function" != typeof a) throw new ie(u);
                                    if (i && !s && "wrapper" == Io(a)) var s = new _n([], !0)
                                }
                                for (n = s ? n : r; ++n < r;) {
                                    var l = Io(a = e[n]),
                                        h = "wrapper" == l ? Oo(a) : o;
                                    s = h && Zo(h[0]) && h[1] == (k | x | w | T) && !h[4].length && 1 == h[9] ? s[Io(h[0])].apply(s, h[3]) : 1 == a.length && Zo(a) ? s[l]() : s.thru(a)
                                }
                                return function () {
                                    var t = arguments,
                                        n = t[0];
                                    if (s && 1 == t.length && _s(n)) return s.plant(n).value();
                                    for (var i = 0, o = r ? e[i].apply(this, t) : n; ++i < r;) o = e[i].call(this, o);
                                    return o
                                }
                            })
                        }

                        function vo(t, e, r, i, a, s, u, l, h, c) {
                            var f = e & k,
                                d = e & _,
                                p = e & y,
                                v = e & (x | b),
                                g = e & C,
                                m = p ? o : co(t);
                            return function _() {
                                for (var y = arguments.length, x = n(y), b = y; b--;) x[b] = arguments[b];
                                if (v) var w = Ro(_),
                                    S = function (t, e) {
                                        for (var r = t.length, n = 0; r--;) t[r] === e && ++n;
                                        return n
                                    }(x, w);
                                if (i && (x = eo(x, i, a, v)), s && (x = ro(x, s, u, v)), y -= S, v && y < c) {
                                    var k = Ar(x, w);
                                    return wo(t, e, vo, _.placeholder, r, x, k, l, h, c - y)
                                }
                                var T = d ? r : this,
                                    C = p ? T[t] : t;
                                return y = x.length, l ? x = function (t, e) {
                                    for (var r = t.length, n = Ur(e.length, r), i = no(t); n--;) {
                                        var a = e[n];
                                        t[n] = Yo(a, r) ? i[a] : o
                                    }
                                    return t
                                }(x, l) : g && y > 1 && x.reverse(), f && h < y && (x.length = h), this && this !== ze && this instanceof _ && (C = m || co(C)), C.apply(T, x)
                            }
                        }

                        function go(t, e) {
                            return function (r, n) {
                                return function (t, e, r, n) {
                                    return Vn(t, function (t, i, o) {
                                        e(n, r(t), i, o)
                                    }), n
                                }(r, t, e(n), {})
                            }
                        }

                        function _o(t, e) {
                            return function (r, n) {
                                var i;
                                if (r === o && n === o) return e;
                                if (r !== o && (i = r), n !== o) {
                                    if (i === o) return n;
                                    "string" == typeof r || "string" == typeof n ? (r = Ei(r), n = Ei(n)) : (r = Ri(r), n = Ri(n)), i = t(r, n)
                                }
                                return i
                            }
                        }

                        function yo(t) {
                            return Bo(function (e) {
                                return e = Je(e, gr(Eo())), ki(function (r) {
                                    var n = this;
                                    return t(e, function (t) {
                                        return qe(t, n, r)
                                    })
                                })
                            })
                        }

                        function mo(t, e) {
                            var r = (e = e === o ? " " : Ei(e)).length;
                            if (r < 2) return r ? Si(e, t) : e;
                            var n = Si(e, jr(t / Br(e)));
                            return kr(e) ? $i(Dr(n), 0, t).join("") : n.slice(0, t)
                        }

                        function xo(t) {
                            return function (e, r, i) {
                                return i && "number" != typeof i && Vo(e, r, i) && (r = i = o), e = Fs(e), r === o ? (r = e, e = 0) : r = Fs(r),
                                    function (t, e, r, i) {
                                        for (var o = -1, a = qr(jr((e - t) / (r || 1)), 0), s = n(a); a--;) s[i ? a : ++o] = t, t += r;
                                        return s
                                    }(e, r, i = i === o ? e < r ? 1 : -1 : Fs(i), t)
                            }
                        }

                        function bo(t) {
                            return function (e, r) {
                                return "string" == typeof e && "string" == typeof r || (e = Hs(e), r = Hs(r)), t(e, r)
                            }
                        }

                        function wo(t, e, r, n, i, a, s, u, l, h) {
                            var c = e & x;
                            e |= c ? w : S, (e &= ~(c ? S : w)) & m || (e &= ~(_ | y));
                            var f = [t, e, i, c ? a : o, c ? s : o, c ? o : a, c ? o : s, u, l, h],
                                d = r.apply(o, f);
                            return Zo(t) && na(d, f), d.placeholder = n, aa(d, t, e)
                        }

                        function So(t) {
                            var e = te[t];
                            return function (t, r) {
                                if (t = Hs(t), r = null == r ? 0 : Ur(Gs(r), 292)) {
                                    var n = (qs(t) + "e").split("e");
                                    return +((n = (qs(e(n[0] + "e" + (+n[1] + r))) + "e").split("e"))[0] + "e" + (+n[1] - r))
                                }
                                return e(t)
                            }
                        }
                        var ko = tn && 1 / Pr(new tn([, -0]))[1] == z ? function (t) {
                            return new tn(t)
                        } : Iu;

                        function To(t) {
                            return function (e) {
                                var r = Ho(e);
                                return r == Z ? Tr(e) : r == rt ? Mr(e) : function (t, e) {
                                    return Je(e, function (e) {
                                        return [e, t[e]]
                                    })
                                }(e, t(e))
                            }
                        }

                        function Co(t, e, r, i, a, s, l, h) {
                            var f = e & y;
                            if (!f && "function" != typeof t) throw new ie(u);
                            var d = i ? i.length : 0;
                            if (d || (e &= ~(w | S), i = a = o), l = l === o ? l : qr(Gs(l), 0), h = h === o ? h : Gs(h), d -= a ? a.length : 0, e & S) {
                                var p = i,
                                    v = a;
                                i = a = o
                            }
                            var g = f ? o : Oo(t),
                                C = [t, e, r, i, a, p, v, s, l, h];
                            if (g && function (t, e) {
                                var r = t[1],
                                    n = e[1],
                                    i = r | n,
                                    o = i < (_ | y | k),
                                    a = n == k && r == x || n == k && r == T && t[7].length <= e[8] || n == (k | T) && e[7].length <= e[8] && r == x;
                                if (!o && !a) return t;
                                n & _ && (t[2] = e[2], i |= r & _ ? 0 : m);
                                var s = e[3];
                                if (s) {
                                    var u = t[3];
                                    t[3] = u ? eo(u, s, e[4]) : s, t[4] = u ? Ar(t[3], c) : e[4]
                                }(s = e[5]) && (u = t[5], t[5] = u ? ro(u, s, e[6]) : s, t[6] = u ? Ar(t[5], c) : e[6]), (s = e[7]) && (t[7] = s), n & k && (t[8] = null == t[8] ? e[8] : Ur(t[8], e[8])), null == t[9] && (t[9] = e[9]), t[0] = e[0], t[1] = i
                            }(C, g), t = C[0], e = C[1], r = C[2], i = C[3], a = C[4], !(h = C[9] = C[9] === o ? f ? 0 : t.length : qr(C[9] - d, 0)) && e & (x | b) && (e &= ~(x | b)), e && e != _) A = e == x || e == b ? function (t, e, r) {
                                var i = co(t);
                                return function a() {
                                    for (var s = arguments.length, u = n(s), l = s, h = Ro(a); l--;) u[l] = arguments[l];
                                    var c = s < 3 && u[0] !== h && u[s - 1] !== h ? [] : Ar(u, h);
                                    return (s -= c.length) < r ? wo(t, e, vo, a.placeholder, o, u, c, o, o, r - s) : qe(this && this !== ze && this instanceof a ? i : t, this, u)
                                }
                            }(t, e, h) : e != w && e != (_ | w) || a.length ? vo.apply(o, C) : function (t, e, r, i) {
                                var o = e & _,
                                    a = co(t);
                                return function e() {
                                    for (var s = -1, u = arguments.length, l = -1, h = i.length, c = n(h + u), f = this && this !== ze && this instanceof e ? a : t; ++l < h;) c[l] = i[l];
                                    for (; u--;) c[l++] = arguments[++s];
                                    return qe(f, o ? r : this, c)
                                }
                            }(t, e, r, i);
                            else var A = function (t, e, r) {
                                var n = e & _,
                                    i = co(t);
                                return function e() {
                                    return (this && this !== ze && this instanceof e ? i : t).apply(n ? r : this, arguments)
                                }
                            }(t, e, r);
                            return aa((g ? Li : na)(A, C), t, e)
                        }

                        function Ao(t, e, r, n) {
                            return t === o || ds(t, se[r]) && !he.call(n, r) ? e : t
                        }

                        function Lo(t, e, r, n, i, a) {
                            return As(t) && As(e) && (a.set(e, t), gi(t, e, o, Lo, a), a.delete(e)), t
                        }

                        function Po(t) {
                            return Bs(t) ? o : t
                        }

                        function Mo(t, e, r, n, i, a) {
                            var s = r & v,
                                u = t.length,
                                l = e.length;
                            if (u != l && !(s && l > u)) return !1;
                            var h = a.get(t);
                            if (h && a.get(e)) return h == e;
                            var c = -1,
                                f = !0,
                                d = r & g ? new wn : o;
                            for (a.set(t, e), a.set(e, t); ++c < u;) {
                                var p = t[c],
                                    _ = e[c];
                                if (n) var y = s ? n(_, p, c, e, t, a) : n(p, _, c, t, e, a);
                                if (y !== o) {
                                    if (y) continue;
                                    f = !1;
                                    break
                                }
                                if (d) {
                                    if (!nr(e, function (t, e) {
                                        if (!yr(d, e) && (p === t || i(p, t, r, n, a))) return d.push(e)
                                    })) {
                                        f = !1;
                                        break
                                    }
                                } else if (p !== _ && !i(p, _, r, n, a)) {
                                    f = !1;
                                    break
                                }
                            }
                            return a.delete(t), a.delete(e), f
                        }

                        function Bo(t) {
                            return oa(ea(t, o, ya), t + "")
                        }

                        function Do(t) {
                            return Kn(t, iu, Go)
                        }

                        function zo(t) {
                            return Kn(t, ou, Wo)
                        }
                        var Oo = nn ? function (t) {
                            return nn.get(t)
                        } : Iu;

                        function Io(t) {
                            for (var e = t.name + "", r = on[e], n = he.call(on, e) ? r.length : 0; n--;) {
                                var i = r[n],
                                    o = i.func;
                                if (null == o || o == t) return i.name
                            }
                            return e
                        }

                        function Ro(t) {
                            return (he.call(pn, "placeholder") ? pn : t).placeholder
                        }

                        function Eo() {
                            var t = pn.iteratee || Bu;
                            return t = t === Bu ? li : t, arguments.length ? t(arguments[0], arguments[1]) : t
                        }

                        function jo(t, e) {
                            var r, n, i = t.__data__;
                            return ("string" == (n = typeof (r = e)) || "number" == n || "symbol" == n || "boolean" == n ? "__proto__" !== r : null === r) ? i["string" == typeof e ? "string" : "hash"] : i.map
                        }

                        function No(t) {
                            for (var e = iu(t), r = e.length; r--;) {
                                var n = e[r],
                                    i = t[n];
                                e[r] = [n, i, Jo(i)]
                            }
                            return e
                        }

                        function Fo(t, e) {
                            var r = function (t, e) {
                                return null == t ? o : t[e]
                            }(t, e);
                            return ui(r) ? r : o
                        }
                        var Go = Fr ? function (t) {
                                return null == t ? [] : (t = ee(t), Ze(Fr(t), function (e) {
                                    return Oe.call(t, e)
                                }))
                            } : Wu,
                            Wo = Fr ? function (t) {
                                for (var e = []; t;) tr(e, Go(t)), t = Be(t);
                                return e
                            } : Wu,
                            Ho = Jn;

                        function Xo(t, e, r) {
                            for (var n = -1, i = (e = Yi(e, t)).length, o = !1; ++n < i;) {
                                var a = ha(e[n]);
                                if (!(o = null != t && r(t, a))) break;
                                t = t[a]
                            }
                            return o || ++n != i ? o : !!(i = null == t ? 0 : t.length) && Cs(i) && Yo(a, i) && (_s(t) || gs(t))
                        }

                        function qo(t) {
                            return "function" != typeof t.constructor || Ko(t) ? {} : vn(Be(t))
                        }

                        function Uo(t) {
                            return _s(t) || gs(t) || !!(Ee && t && t[Ee])
                        }

                        function Yo(t, e) {
                            var r = typeof t;
                            return !!(e = null == e ? O : e) && ("number" == r || "symbol" != r && Vt.test(t)) && t > -1 && t % 1 == 0 && t < e
                        }

                        function Vo(t, e, r) {
                            if (!As(r)) return !1;
                            var n = typeof e;
                            return !!("number" == n ? ms(r) && Yo(e, r.length) : "string" == n && e in r) && ds(r[e], t)
                        }

                        function $o(t, e) {
                            if (_s(t)) return !1;
                            var r = typeof t;
                            return !("number" != r && "symbol" != r && "boolean" != r && null != t && !Is(t)) || Mt.test(t) || !Pt.test(t) || null != e && t in ee(e)
                        }

                        function Zo(t) {
                            var e = Io(t),
                                r = pn[e];
                            if ("function" != typeof r || !(e in yn.prototype)) return !1;
                            if (t === r) return !0;
                            var n = Oo(r);
                            return !!n && t === n[0]
                        }(Qr && Ho(new Qr(new ArrayBuffer(1))) != lt || Kr && Ho(new Kr) != Z || Jr && "[object Promise]" != Ho(Jr.resolve()) || tn && Ho(new tn) != rt || en && Ho(new en) != at) && (Ho = function (t) {
                            var e = Jn(t),
                                r = e == J ? t.constructor : o,
                                n = r ? ca(r) : "";
                            if (n) switch (n) {
                            case an:
                                return lt;
                            case sn:
                                return Z;
                            case un:
                                return "[object Promise]";
                            case ln:
                                return rt;
                            case hn:
                                return at
                            }
                            return e
                        });
                        var Qo = ue ? ks : Hu;

                        function Ko(t) {
                            var e = t && t.constructor;
                            return t === ("function" == typeof e && e.prototype || se)
                        }

                        function Jo(t) {
                            return t == t && !As(t)
                        }

                        function ta(t, e) {
                            return function (r) {
                                return null != r && r[t] === e && (e !== o || t in ee(r))
                            }
                        }

                        function ea(t, e, r) {
                            return e = qr(e === o ? t.length - 1 : e, 0),
                                function () {
                                    for (var i = arguments, o = -1, a = qr(i.length - e, 0), s = n(a); ++o < a;) s[o] = i[e + o];
                                    o = -1;
                                    for (var u = n(e + 1); ++o < e;) u[o] = i[o];
                                    return u[e] = r(s), qe(t, this, u)
                                }
                        }

                        function ra(t, e) {
                            return e.length < 2 ? t : Qn(t, Bi(e, 0, -1))
                        }
                        var na = sa(Li),
                            ia = Er || function (t, e) {
                                return ze.setTimeout(t, e)
                            },
                            oa = sa(Pi);

                        function aa(t, e, r) {
                            var n = e + "";
                            return oa(t, function (t, e) {
                                var r = e.length;
                                if (!r) return t;
                                var n = r - 1;
                                return e[n] = (r > 1 ? "& " : "") + e[n], e = e.join(r > 2 ? ", " : " "), t.replace(Et, "{\n/* [wrapped with " + e + "] */\n")
                            }(n, function (t, e) {
                                return Ye(F, function (r) {
                                    var n = "_." + r[0];
                                    e & r[1] && !Qe(t, n) && t.push(n)
                                }), t.sort()
                            }(function (t) {
                                var e = t.match(jt);
                                return e ? e[1].split(Nt) : []
                            }(n), r)))
                        }

                        function sa(t) {
                            var e = 0,
                                r = 0;
                            return function () {
                                var n = Yr(),
                                    i = M - (n - r);
                                if (r = n, i > 0) {
                                    if (++e >= P) return arguments[0]
                                } else e = 0;
                                return t.apply(o, arguments)
                            }
                        }

                        function ua(t, e) {
                            var r = -1,
                                n = t.length,
                                i = n - 1;
                            for (e = e === o ? n : e; ++r < e;) {
                                var a = wi(r, i),
                                    s = t[a];
                                t[a] = t[r], t[r] = s
                            }
                            return t.length = e, t
                        }
                        var la = function (t) {
                            var e = ss(t, function (t) {
                                    return r.size === h && r.clear(), t
                                }),
                                r = e.cache;
                            return e
                        }(function (t) {
                            var e = [];
                            return 46 === t.charCodeAt(0) && e.push(""), t.replace(Bt, function (t, r, n, i) {
                                e.push(n ? i.replace(Gt, "$1") : r || t)
                            }), e
                        });

                        function ha(t) {
                            if ("string" == typeof t || Is(t)) return t;
                            var e = t + "";
                            return "0" == e && 1 / t == -z ? "-0" : e
                        }

                        function ca(t) {
                            if (null != t) {
                                try {
                                    return le.call(t)
                                } catch (t) {}
                                try {
                                    return t + ""
                                } catch (t) {}
                            }
                            return ""
                        }

                        function fa(t) {
                            if (t instanceof yn) return t.clone();
                            var e = new _n(t.__wrapped__, t.__chain__);
                            return e.__actions__ = no(t.__actions__), e.__index__ = t.__index__, e.__values__ = t.__values__, e
                        }
                        var da = ki(function (t, e) {
                                return xs(t) ? Nn(t, qn(e, 1, xs, !0)) : []
                            }),
                            pa = ki(function (t, e) {
                                var r = Sa(e);
                                return xs(r) && (r = o), xs(t) ? Nn(t, qn(e, 1, xs, !0), Eo(r, 2)) : []
                            }),
                            va = ki(function (t, e) {
                                var r = Sa(e);
                                return xs(r) && (r = o), xs(t) ? Nn(t, qn(e, 1, xs, !0), o, r) : []
                            });

                        function ga(t, e, r) {
                            var n = null == t ? 0 : t.length;
                            if (!n) return -1;
                            var i = null == r ? 0 : Gs(r);
                            return i < 0 && (i = qr(n + i, 0)), ar(t, Eo(e, 3), i)
                        }

                        function _a(t, e, r) {
                            var n = null == t ? 0 : t.length;
                            if (!n) return -1;
                            var i = n - 1;
                            return r !== o && (i = Gs(r), i = r < 0 ? qr(n + i, 0) : Ur(i, n - 1)), ar(t, Eo(e, 3), i, !0)
                        }

                        function ya(t) {
                            return null != t && t.length ? qn(t, 1) : []
                        }

                        function ma(t) {
                            return t && t.length ? t[0] : o
                        }
                        var xa = ki(function (t) {
                                var e = Je(t, qi);
                                return e.length && e[0] === t[0] ? ni(e) : []
                            }),
                            ba = ki(function (t) {
                                var e = Sa(t),
                                    r = Je(t, qi);
                                return e === Sa(r) ? e = o : r.pop(), r.length && r[0] === t[0] ? ni(r, Eo(e, 2)) : []
                            }),
                            wa = ki(function (t) {
                                var e = Sa(t),
                                    r = Je(t, qi);
                                return (e = "function" == typeof e ? e : o) && r.pop(), r.length && r[0] === t[0] ? ni(r, o, e) : []
                            });

                        function Sa(t) {
                            var e = null == t ? 0 : t.length;
                            return e ? t[e - 1] : o
                        }
                        var ka = ki(Ta);

                        function Ta(t, e) {
                            return t && t.length && e && e.length ? xi(t, e) : t
                        }
                        var Ca = Bo(function (t, e) {
                            var r = null == t ? 0 : t.length,
                                n = On(t, e);
                            return bi(t, Je(e, function (t) {
                                return Yo(t, r) ? +t : t
                            }).sort(to)), n
                        });

                        function Aa(t) {
                            return null == t ? t : Zr.call(t)
                        }
                        var La = ki(function (t) {
                                return ji(qn(t, 1, xs, !0))
                            }),
                            Pa = ki(function (t) {
                                var e = Sa(t);
                                return xs(e) && (e = o), ji(qn(t, 1, xs, !0), Eo(e, 2))
                            }),
                            Ma = ki(function (t) {
                                var e = Sa(t);
                                return e = "function" == typeof e ? e : o, ji(qn(t, 1, xs, !0), o, e)
                            });

                        function Ba(t) {
                            if (!t || !t.length) return [];
                            var e = 0;
                            return t = Ze(t, function (t) {
                                if (xs(t)) return e = qr(t.length, e), !0
                            }), vr(e, function (e) {
                                return Je(t, cr(e))
                            })
                        }

                        function Da(t, e) {
                            if (!t || !t.length) return [];
                            var r = Ba(t);
                            return null == e ? r : Je(r, function (t) {
                                return qe(e, o, t)
                            })
                        }
                        var za = ki(function (t, e) {
                                return xs(t) ? Nn(t, e) : []
                            }),
                            Oa = ki(function (t) {
                                return Hi(Ze(t, xs))
                            }),
                            Ia = ki(function (t) {
                                var e = Sa(t);
                                return xs(e) && (e = o), Hi(Ze(t, xs), Eo(e, 2))
                            }),
                            Ra = ki(function (t) {
                                var e = Sa(t);
                                return e = "function" == typeof e ? e : o, Hi(Ze(t, xs), o, e)
                            }),
                            Ea = ki(Ba);
                        var ja = ki(function (t) {
                            var e = t.length,
                                r = e > 1 ? t[e - 1] : o;
                            return Da(t, r = "function" == typeof r ? (t.pop(), r) : o)
                        });

                        function Na(t) {
                            var e = pn(t);
                            return e.__chain__ = !0, e
                        }

                        function Fa(t, e) {
                            return e(t)
                        }
                        var Ga = Bo(function (t) {
                            var e = t.length,
                                r = e ? t[0] : 0,
                                n = this.__wrapped__,
                                i = function (e) {
                                    return On(e, t)
                                };
                            return !(e > 1 || this.__actions__.length) && n instanceof yn && Yo(r) ? ((n = n.slice(r, +r + (e ? 1 : 0))).__actions__.push({
                                func: Fa,
                                args: [i],
                                thisArg: o
                            }), new _n(n, this.__chain__).thru(function (t) {
                                return e && !t.length && t.push(o), t
                            })) : this.thru(i)
                        });
                        var Wa = oo(function (t, e, r) {
                            he.call(t, r) ? ++t[r] : zn(t, r, 1)
                        });
                        var Ha = fo(ga),
                            Xa = fo(_a);

                        function qa(t, e) {
                            return (_s(t) ? Ye : Fn)(t, Eo(e, 3))
                        }

                        function Ua(t, e) {
                            return (_s(t) ? Ve : Gn)(t, Eo(e, 3))
                        }
                        var Ya = oo(function (t, e, r) {
                            he.call(t, r) ? t[r].push(e) : zn(t, r, [e])
                        });
                        var Va = ki(function (t, e, r) {
                                var i = -1,
                                    o = "function" == typeof e,
                                    a = ms(t) ? n(t.length) : [];
                                return Fn(t, function (t) {
                                    a[++i] = o ? qe(e, t, r) : ii(t, e, r)
                                }), a
                            }),
                            $a = oo(function (t, e, r) {
                                zn(t, r, e)
                            });

                        function Za(t, e) {
                            return (_s(t) ? Je : di)(t, Eo(e, 3))
                        }
                        var Qa = oo(function (t, e, r) {
                            t[r ? 0 : 1].push(e)
                        }, function () {
                            return [
                                [],
                                []
                            ]
                        });
                        var Ka = ki(function (t, e) {
                                if (null == t) return [];
                                var r = e.length;
                                return r > 1 && Vo(t, e[0], e[1]) ? e = [] : r > 2 && Vo(e[0], e[1], e[2]) && (e = [e[0]]), yi(t, qn(e, 1), [])
                            }),
                            Ja = Rr || function () {
                                return ze.Date.now()
                            };

                        function ts(t, e, r) {
                            return e = r ? o : e, e = t && null == e ? t.length : e, Co(t, k, o, o, o, o, e)
                        }

                        function es(t, e) {
                            var r;
                            if ("function" != typeof e) throw new ie(u);
                            return t = Gs(t),
                                function () {
                                    return --t > 0 && (r = e.apply(this, arguments)), t <= 1 && (e = o), r
                                }
                        }
                        var rs = ki(function (t, e, r) {
                                var n = _;
                                if (r.length) {
                                    var i = Ar(r, Ro(rs));
                                    n |= w
                                }
                                return Co(t, n, e, r, i)
                            }),
                            ns = ki(function (t, e, r) {
                                var n = _ | y;
                                if (r.length) {
                                    var i = Ar(r, Ro(ns));
                                    n |= w
                                }
                                return Co(e, n, t, r, i)
                            });

                        function is(t, e, r) {
                            var n, i, a, s, l, h, c = 0,
                                f = !1,
                                d = !1,
                                p = !0;
                            if ("function" != typeof t) throw new ie(u);

                            function v(e) {
                                var r = n,
                                    a = i;
                                return n = i = o, c = e, s = t.apply(a, r)
                            }

                            function g(t) {
                                var r = t - h;
                                return h === o || r >= e || r < 0 || d && t - c >= a
                            }

                            function _() {
                                var t = Ja();
                                if (g(t)) return y(t);
                                l = ia(_, function (t) {
                                    var r = e - (t - h);
                                    return d ? Ur(r, a - (t - c)) : r
                                }(t))
                            }

                            function y(t) {
                                return l = o, p && n ? v(t) : (n = i = o, s)
                            }

                            function m() {
                                var t = Ja(),
                                    r = g(t);
                                if (n = arguments, i = this, h = t, r) {
                                    if (l === o) return function (t) {
                                        return c = t, l = ia(_, e), f ? v(t) : s
                                    }(h);
                                    if (d) return l = ia(_, e), v(h)
                                }
                                return l === o && (l = ia(_, e)), s
                            }
                            return e = Hs(e) || 0, As(r) && (f = !!r.leading, a = (d = "maxWait" in r) ? qr(Hs(r.maxWait) || 0, e) : a, p = "trailing" in r ? !!r.trailing : p), m.cancel = function () {
                                l !== o && Zi(l), c = 0, n = h = i = l = o
                            }, m.flush = function () {
                                return l === o ? s : y(Ja())
                            }, m
                        }
                        var os = ki(function (t, e) {
                                return jn(t, 1, e)
                            }),
                            as = ki(function (t, e, r) {
                                return jn(t, Hs(e) || 0, r)
                            });

                        function ss(t, e) {
                            if ("function" != typeof t || null != e && "function" != typeof e) throw new ie(u);
                            var r = function () {
                                var n = arguments,
                                    i = e ? e.apply(this, n) : n[0],
                                    o = r.cache;
                                if (o.has(i)) return o.get(i);
                                var a = t.apply(this, n);
                                return r.cache = o.set(i, a) || o, a
                            };
                            return r.cache = new(ss.Cache || bn), r
                        }

                        function us(t) {
                            if ("function" != typeof t) throw new ie(u);
                            return function () {
                                var e = arguments;
                                switch (e.length) {
                                case 0:
                                    return !t.call(this);
                                case 1:
                                    return !t.call(this, e[0]);
                                case 2:
                                    return !t.call(this, e[0], e[1]);
                                case 3:
                                    return !t.call(this, e[0], e[1], e[2])
                                }
                                return !t.apply(this, e)
                            }
                        }
                        ss.Cache = bn;
                        var ls = Vi(function (t, e) {
                                var r = (e = 1 == e.length && _s(e[0]) ? Je(e[0], gr(Eo())) : Je(qn(e, 1), gr(Eo()))).length;
                                return ki(function (n) {
                                    for (var i = -1, o = Ur(n.length, r); ++i < o;) n[i] = e[i].call(this, n[i]);
                                    return qe(t, this, n)
                                })
                            }),
                            hs = ki(function (t, e) {
                                var r = Ar(e, Ro(hs));
                                return Co(t, w, o, e, r)
                            }),
                            cs = ki(function (t, e) {
                                var r = Ar(e, Ro(cs));
                                return Co(t, S, o, e, r)
                            }),
                            fs = Bo(function (t, e) {
                                return Co(t, T, o, o, o, e)
                            });

                        function ds(t, e) {
                            return t === e || t != t && e != e
                        }
                        var ps = bo(ti),
                            vs = bo(function (t, e) {
                                return t >= e
                            }),
                            gs = oi(function () {
                                return arguments
                            }()) ? oi : function (t) {
                                return Ls(t) && he.call(t, "callee") && !Oe.call(t, "callee")
                            },
                            _s = n.isArray,
                            ys = Ne ? gr(Ne) : function (t) {
                                return Ls(t) && Jn(t) == ut
                            };

                        function ms(t) {
                            return null != t && Cs(t.length) && !ks(t)
                        }

                        function xs(t) {
                            return Ls(t) && ms(t)
                        }
                        var bs = Gr || Hu,
                            ws = Fe ? gr(Fe) : function (t) {
                                return Ls(t) && Jn(t) == q
                            };

                        function Ss(t) {
                            if (!Ls(t)) return !1;
                            var e = Jn(t);
                            return e == Y || e == U || "string" == typeof t.message && "string" == typeof t.name && !Bs(t)
                        }

                        function ks(t) {
                            if (!As(t)) return !1;
                            var e = Jn(t);
                            return e == V || e == $ || e == H || e == tt
                        }

                        function Ts(t) {
                            return "number" == typeof t && t == Gs(t)
                        }

                        function Cs(t) {
                            return "number" == typeof t && t > -1 && t % 1 == 0 && t <= O
                        }

                        function As(t) {
                            var e = typeof t;
                            return null != t && ("object" == e || "function" == e)
                        }

                        function Ls(t) {
                            return null != t && "object" == typeof t
                        }
                        var Ps = Ge ? gr(Ge) : function (t) {
                            return Ls(t) && Ho(t) == Z
                        };

                        function Ms(t) {
                            return "number" == typeof t || Ls(t) && Jn(t) == Q
                        }

                        function Bs(t) {
                            if (!Ls(t) || Jn(t) != J) return !1;
                            var e = Be(t);
                            if (null === e) return !0;
                            var r = he.call(e, "constructor") && e.constructor;
                            return "function" == typeof r && r instanceof r && le.call(r) == pe
                        }
                        var Ds = We ? gr(We) : function (t) {
                            return Ls(t) && Jn(t) == et
                        };
                        var zs = He ? gr(He) : function (t) {
                            return Ls(t) && Ho(t) == rt
                        };

                        function Os(t) {
                            return "string" == typeof t || !_s(t) && Ls(t) && Jn(t) == nt
                        }

                        function Is(t) {
                            return "symbol" == typeof t || Ls(t) && Jn(t) == it
                        }
                        var Rs = Xe ? gr(Xe) : function (t) {
                            return Ls(t) && Cs(t.length) && !!Ce[Jn(t)]
                        };
                        var Es = bo(fi),
                            js = bo(function (t, e) {
                                return t <= e
                            });

                        function Ns(t) {
                            if (!t) return [];
                            if (ms(t)) return Os(t) ? Dr(t) : no(t);
                            if (je && t[je]) return function (t) {
                                for (var e, r = []; !(e = t.next()).done;) r.push(e.value);
                                return r
                            }(t[je]());
                            var e = Ho(t);
                            return (e == Z ? Tr : e == rt ? Pr : du)(t)
                        }

                        function Fs(t) {
                            return t ? (t = Hs(t)) === z || t === -z ? (t < 0 ? -1 : 1) * I : t == t ? t : 0 : 0 === t ? t : 0
                        }

                        function Gs(t) {
                            var e = Fs(t),
                                r = e % 1;
                            return e == e ? r ? e - r : e : 0
                        }

                        function Ws(t) {
                            return t ? In(Gs(t), 0, E) : 0
                        }

                        function Hs(t) {
                            if ("number" == typeof t) return t;
                            if (Is(t)) return R;
                            if (As(t)) {
                                var e = "function" == typeof t.valueOf ? t.valueOf() : t;
                                t = As(e) ? e + "" : e
                            }
                            if ("string" != typeof t) return 0 === t ? t : +t;
                            t = t.replace(Ot, "");
                            var r = qt.test(t);
                            return r || Yt.test(t) ? Me(t.slice(2), r ? 2 : 8) : Xt.test(t) ? R : +t
                        }

                        function Xs(t) {
                            return io(t, ou(t))
                        }

                        function qs(t) {
                            return null == t ? "" : Ei(t)
                        }
                        var Us = ao(function (t, e) {
                                if (Ko(e) || ms(e)) io(e, iu(e), t);
                                else
                                    for (var r in e) he.call(e, r) && Pn(t, r, e[r])
                            }),
                            Ys = ao(function (t, e) {
                                io(e, ou(e), t)
                            }),
                            Vs = ao(function (t, e, r, n) {
                                io(e, ou(e), t, n)
                            }),
                            $s = ao(function (t, e, r, n) {
                                io(e, iu(e), t, n)
                            }),
                            Zs = Bo(On);
                        var Qs = ki(function (t, e) {
                                t = ee(t);
                                var r = -1,
                                    n = e.length,
                                    i = n > 2 ? e[2] : o;
                                for (i && Vo(e[0], e[1], i) && (n = 1); ++r < n;)
                                    for (var a = e[r], s = ou(a), u = -1, l = s.length; ++u < l;) {
                                        var h = s[u],
                                            c = t[h];
                                        (c === o || ds(c, se[h]) && !he.call(t, h)) && (t[h] = a[h])
                                    }
                                return t
                            }),
                            Ks = ki(function (t) {
                                return t.push(o, Lo), qe(su, o, t)
                            });

                        function Js(t, e, r) {
                            var n = null == t ? o : Qn(t, e);
                            return n === o ? r : n
                        }

                        function tu(t, e) {
                            return null != t && Xo(t, e, ri)
                        }
                        var eu = go(function (t, e, r) {
                                null != e && "function" != typeof e.toString && (e = de.call(e)), t[e] = r
                            }, Au(Mu)),
                            ru = go(function (t, e, r) {
                                null != e && "function" != typeof e.toString && (e = de.call(e)), he.call(t, e) ? t[e].push(r) : t[e] = [r]
                            }, Eo),
                            nu = ki(ii);

                        function iu(t) {
                            return ms(t) ? kn(t) : hi(t)
                        }

                        function ou(t) {
                            return ms(t) ? kn(t, !0) : ci(t)
                        }
                        var au = ao(function (t, e, r) {
                                gi(t, e, r)
                            }),
                            su = ao(function (t, e, r, n) {
                                gi(t, e, r, n)
                            }),
                            uu = Bo(function (t, e) {
                                var r = {};
                                if (null == t) return r;
                                var n = !1;
                                e = Je(e, function (e) {
                                    return e = Yi(e, t), n || (n = e.length > 1), e
                                }), io(t, zo(t), r), n && (r = Rn(r, f | d | p, Po));
                                for (var i = e.length; i--;) Ni(r, e[i]);
                                return r
                            });
                        var lu = Bo(function (t, e) {
                            return null == t ? {} : function (t, e) {
                                return mi(t, e, function (e, r) {
                                    return tu(t, r)
                                })
                            }(t, e)
                        });

                        function hu(t, e) {
                            if (null == t) return {};
                            var r = Je(zo(t), function (t) {
                                return [t]
                            });
                            return e = Eo(e), mi(t, r, function (t, r) {
                                return e(t, r[0])
                            })
                        }
                        var cu = To(iu),
                            fu = To(ou);

                        function du(t) {
                            return null == t ? [] : _r(t, iu(t))
                        }
                        var pu = ho(function (t, e, r) {
                            return e = e.toLowerCase(), t + (r ? vu(e) : e)
                        });

                        function vu(t) {
                            return Su(qs(t).toLowerCase())
                        }

                        function gu(t) {
                            return (t = qs(t)) && t.replace($t, br).replace(me, "")
                        }
                        var _u = ho(function (t, e, r) {
                                return t + (r ? "-" : "") + e.toLowerCase()
                            }),
                            yu = ho(function (t, e, r) {
                                return t + (r ? " " : "") + e.toLowerCase()
                            }),
                            mu = lo("toLowerCase");
                        var xu = ho(function (t, e, r) {
                            return t + (r ? "_" : "") + e.toLowerCase()
                        });
                        var bu = ho(function (t, e, r) {
                            return t + (r ? " " : "") + Su(e)
                        });
                        var wu = ho(function (t, e, r) {
                                return t + (r ? " " : "") + e.toUpperCase()
                            }),
                            Su = lo("toUpperCase");

                        function ku(t, e, r) {
                            return t = qs(t), (e = r ? o : e) === o ? function (t) {
                                return Se.test(t)
                            }(t) ? function (t) {
                                return t.match(be) || []
                            }(t) : function (t) {
                                return t.match(Ft) || []
                            }(t) : t.match(e) || []
                        }
                        var Tu = ki(function (t, e) {
                                try {
                                    return qe(t, o, e)
                                } catch (t) {
                                    return Ss(t) ? t : new Kt(t)
                                }
                            }),
                            Cu = Bo(function (t, e) {
                                return Ye(e, function (e) {
                                    e = ha(e), zn(t, e, rs(t[e], t))
                                }), t
                            });

                        function Au(t) {
                            return function () {
                                return t
                            }
                        }
                        var Lu = po(),
                            Pu = po(!0);

                        function Mu(t) {
                            return t
                        }

                        function Bu(t) {
                            return li("function" == typeof t ? t : Rn(t, f))
                        }
                        var Du = ki(function (t, e) {
                                return function (r) {
                                    return ii(r, t, e)
                                }
                            }),
                            zu = ki(function (t, e) {
                                return function (r) {
                                    return ii(t, r, e)
                                }
                            });

                        function Ou(t, e, r) {
                            var n = iu(e),
                                i = Zn(e, n);
                            null != r || As(e) && (i.length || !n.length) || (r = e, e = t, t = this, i = Zn(e, iu(e)));
                            var o = !(As(r) && "chain" in r && !r.chain),
                                a = ks(t);
                            return Ye(i, function (r) {
                                var n = e[r];
                                t[r] = n, a && (t.prototype[r] = function () {
                                    var e = this.__chain__;
                                    if (o || e) {
                                        var r = t(this.__wrapped__);
                                        return (r.__actions__ = no(this.__actions__)).push({
                                            func: n,
                                            args: arguments,
                                            thisArg: t
                                        }), r.__chain__ = e, r
                                    }
                                    return n.apply(t, tr([this.value()], arguments))
                                })
                            }), t
                        }

                        function Iu() {}
                        var Ru = yo(Je),
                            Eu = yo($e),
                            ju = yo(nr);

                        function Nu(t) {
                            return $o(t) ? cr(ha(t)) : function (t) {
                                return function (e) {
                                    return Qn(e, t)
                                }
                            }(t)
                        }
                        var Fu = xo(),
                            Gu = xo(!0);

                        function Wu() {
                            return []
                        }

                        function Hu() {
                            return !1
                        }
                        var Xu = _o(function (t, e) {
                                return t + e
                            }, 0),
                            qu = So("ceil"),
                            Uu = _o(function (t, e) {
                                return t / e
                            }, 1),
                            Yu = So("floor");
                        var Vu, $u = _o(function (t, e) {
                                return t * e
                            }, 1),
                            Zu = So("round"),
                            Qu = _o(function (t, e) {
                                return t - e
                            }, 0);
                        return pn.after = function (t, e) {
                            if ("function" != typeof e) throw new ie(u);
                            return t = Gs(t),
                                function () {
                                    if (--t < 1) return e.apply(this, arguments)
                                }
                        }, pn.ary = ts, pn.assign = Us, pn.assignIn = Ys, pn.assignInWith = Vs, pn.assignWith = $s, pn.at = Zs, pn.before = es, pn.bind = rs, pn.bindAll = Cu, pn.bindKey = ns, pn.castArray = function () {
                            if (!arguments.length) return [];
                            var t = arguments[0];
                            return _s(t) ? t : [t]
                        }, pn.chain = Na, pn.chunk = function (t, e, r) {
                            e = (r ? Vo(t, e, r) : e === o) ? 1 : qr(Gs(e), 0);
                            var i = null == t ? 0 : t.length;
                            if (!i || e < 1) return [];
                            for (var a = 0, s = 0, u = n(jr(i / e)); a < i;) u[s++] = Bi(t, a, a += e);
                            return u
                        }, pn.compact = function (t) {
                            for (var e = -1, r = null == t ? 0 : t.length, n = 0, i = []; ++e < r;) {
                                var o = t[e];
                                o && (i[n++] = o)
                            }
                            return i
                        }, pn.concat = function () {
                            var t = arguments.length;
                            if (!t) return [];
                            for (var e = n(t - 1), r = arguments[0], i = t; i--;) e[i - 1] = arguments[i];
                            return tr(_s(r) ? no(r) : [r], qn(e, 1))
                        }, pn.cond = function (t) {
                            var e = null == t ? 0 : t.length,
                                r = Eo();
                            return t = e ? Je(t, function (t) {
                                if ("function" != typeof t[1]) throw new ie(u);
                                return [r(t[0]), t[1]]
                            }) : [], ki(function (r) {
                                for (var n = -1; ++n < e;) {
                                    var i = t[n];
                                    if (qe(i[0], this, r)) return qe(i[1], this, r)
                                }
                            })
                        }, pn.conforms = function (t) {
                            return function (t) {
                                var e = iu(t);
                                return function (r) {
                                    return En(r, t, e)
                                }
                            }(Rn(t, f))
                        }, pn.constant = Au, pn.countBy = Wa, pn.create = function (t, e) {
                            var r = vn(t);
                            return null == e ? r : Dn(r, e)
                        }, pn.curry = function t(e, r, n) {
                            var i = Co(e, x, o, o, o, o, o, r = n ? o : r);
                            return i.placeholder = t.placeholder, i
                        }, pn.curryRight = function t(e, r, n) {
                            var i = Co(e, b, o, o, o, o, o, r = n ? o : r);
                            return i.placeholder = t.placeholder, i
                        }, pn.debounce = is, pn.defaults = Qs, pn.defaultsDeep = Ks, pn.defer = os, pn.delay = as, pn.difference = da, pn.differenceBy = pa, pn.differenceWith = va, pn.drop = function (t, e, r) {
                            var n = null == t ? 0 : t.length;
                            return n ? Bi(t, (e = r || e === o ? 1 : Gs(e)) < 0 ? 0 : e, n) : []
                        }, pn.dropRight = function (t, e, r) {
                            var n = null == t ? 0 : t.length;
                            return n ? Bi(t, 0, (e = n - (e = r || e === o ? 1 : Gs(e))) < 0 ? 0 : e) : []
                        }, pn.dropRightWhile = function (t, e) {
                            return t && t.length ? Gi(t, Eo(e, 3), !0, !0) : []
                        }, pn.dropWhile = function (t, e) {
                            return t && t.length ? Gi(t, Eo(e, 3), !0) : []
                        }, pn.fill = function (t, e, r, n) {
                            var i = null == t ? 0 : t.length;
                            return i ? (r && "number" != typeof r && Vo(t, e, r) && (r = 0, n = i), function (t, e, r, n) {
                                var i = t.length;
                                for ((r = Gs(r)) < 0 && (r = -r > i ? 0 : i + r), (n = n === o || n > i ? i : Gs(n)) < 0 && (n += i), n = r > n ? 0 : Ws(n); r < n;) t[r++] = e;
                                return t
                            }(t, e, r, n)) : []
                        }, pn.filter = function (t, e) {
                            return (_s(t) ? Ze : Xn)(t, Eo(e, 3))
                        }, pn.flatMap = function (t, e) {
                            return qn(Za(t, e), 1)
                        }, pn.flatMapDeep = function (t, e) {
                            return qn(Za(t, e), z)
                        }, pn.flatMapDepth = function (t, e, r) {
                            return r = r === o ? 1 : Gs(r), qn(Za(t, e), r)
                        }, pn.flatten = ya, pn.flattenDeep = function (t) {
                            return null != t && t.length ? qn(t, z) : []
                        }, pn.flattenDepth = function (t, e) {
                            return null != t && t.length ? qn(t, e = e === o ? 1 : Gs(e)) : []
                        }, pn.flip = function (t) {
                            return Co(t, C)
                        }, pn.flow = Lu, pn.flowRight = Pu, pn.fromPairs = function (t) {
                            for (var e = -1, r = null == t ? 0 : t.length, n = {}; ++e < r;) {
                                var i = t[e];
                                n[i[0]] = i[1]
                            }
                            return n
                        }, pn.functions = function (t) {
                            return null == t ? [] : Zn(t, iu(t))
                        }, pn.functionsIn = function (t) {
                            return null == t ? [] : Zn(t, ou(t))
                        }, pn.groupBy = Ya, pn.initial = function (t) {
                            return null != t && t.length ? Bi(t, 0, -1) : []
                        }, pn.intersection = xa, pn.intersectionBy = ba, pn.intersectionWith = wa, pn.invert = eu, pn.invertBy = ru, pn.invokeMap = Va, pn.iteratee = Bu, pn.keyBy = $a, pn.keys = iu, pn.keysIn = ou, pn.map = Za, pn.mapKeys = function (t, e) {
                            var r = {};
                            return e = Eo(e, 3), Vn(t, function (t, n, i) {
                                zn(r, e(t, n, i), t)
                            }), r
                        }, pn.mapValues = function (t, e) {
                            var r = {};
                            return e = Eo(e, 3), Vn(t, function (t, n, i) {
                                zn(r, n, e(t, n, i))
                            }), r
                        }, pn.matches = function (t) {
                            return pi(Rn(t, f))
                        }, pn.matchesProperty = function (t, e) {
                            return vi(t, Rn(e, f))
                        }, pn.memoize = ss, pn.merge = au, pn.mergeWith = su, pn.method = Du, pn.methodOf = zu, pn.mixin = Ou, pn.negate = us, pn.nthArg = function (t) {
                            return t = Gs(t), ki(function (e) {
                                return _i(e, t)
                            })
                        }, pn.omit = uu, pn.omitBy = function (t, e) {
                            return hu(t, us(Eo(e)))
                        }, pn.once = function (t) {
                            return es(2, t)
                        }, pn.orderBy = function (t, e, r, n) {
                            return null == t ? [] : (_s(e) || (e = null == e ? [] : [e]), _s(r = n ? o : r) || (r = null == r ? [] : [r]), yi(t, e, r))
                        }, pn.over = Ru, pn.overArgs = ls, pn.overEvery = Eu, pn.overSome = ju, pn.partial = hs, pn.partialRight = cs, pn.partition = Qa, pn.pick = lu, pn.pickBy = hu, pn.property = Nu, pn.propertyOf = function (t) {
                            return function (e) {
                                return null == t ? o : Qn(t, e)
                            }
                        }, pn.pull = ka, pn.pullAll = Ta, pn.pullAllBy = function (t, e, r) {
                            return t && t.length && e && e.length ? xi(t, e, Eo(r, 2)) : t
                        }, pn.pullAllWith = function (t, e, r) {
                            return t && t.length && e && e.length ? xi(t, e, o, r) : t
                        }, pn.pullAt = Ca, pn.range = Fu, pn.rangeRight = Gu, pn.rearg = fs, pn.reject = function (t, e) {
                            return (_s(t) ? Ze : Xn)(t, us(Eo(e, 3)))
                        }, pn.remove = function (t, e) {
                            var r = [];
                            if (!t || !t.length) return r;
                            var n = -1,
                                i = [],
                                o = t.length;
                            for (e = Eo(e, 3); ++n < o;) {
                                var a = t[n];
                                e(a, n, t) && (r.push(a), i.push(n))
                            }
                            return bi(t, i), r
                        }, pn.rest = function (t, e) {
                            if ("function" != typeof t) throw new ie(u);
                            return ki(t, e = e === o ? e : Gs(e))
                        }, pn.reverse = Aa, pn.sampleSize = function (t, e, r) {
                            return e = (r ? Vo(t, e, r) : e === o) ? 1 : Gs(e), (_s(t) ? Cn : Ci)(t, e)
                        }, pn.set = function (t, e, r) {
                            return null == t ? t : Ai(t, e, r)
                        }, pn.setWith = function (t, e, r, n) {
                            return n = "function" == typeof n ? n : o, null == t ? t : Ai(t, e, r, n)
                        }, pn.shuffle = function (t) {
                            return (_s(t) ? An : Mi)(t)
                        }, pn.slice = function (t, e, r) {
                            var n = null == t ? 0 : t.length;
                            return n ? (r && "number" != typeof r && Vo(t, e, r) ? (e = 0, r = n) : (e = null == e ? 0 : Gs(e), r = r === o ? n : Gs(r)), Bi(t, e, r)) : []
                        }, pn.sortBy = Ka, pn.sortedUniq = function (t) {
                            return t && t.length ? Ii(t) : []
                        }, pn.sortedUniqBy = function (t, e) {
                            return t && t.length ? Ii(t, Eo(e, 2)) : []
                        }, pn.split = function (t, e, r) {
                            return r && "number" != typeof r && Vo(t, e, r) && (e = r = o), (r = r === o ? E : r >>> 0) ? (t = qs(t)) && ("string" == typeof e || null != e && !Ds(e)) && !(e = Ei(e)) && kr(t) ? $i(Dr(t), 0, r) : t.split(e, r) : []
                        }, pn.spread = function (t, e) {
                            if ("function" != typeof t) throw new ie(u);
                            return e = null == e ? 0 : qr(Gs(e), 0), ki(function (r) {
                                var n = r[e],
                                    i = $i(r, 0, e);
                                return n && tr(i, n), qe(t, this, i)
                            })
                        }, pn.tail = function (t) {
                            var e = null == t ? 0 : t.length;
                            return e ? Bi(t, 1, e) : []
                        }, pn.take = function (t, e, r) {
                            return t && t.length ? Bi(t, 0, (e = r || e === o ? 1 : Gs(e)) < 0 ? 0 : e) : []
                        }, pn.takeRight = function (t, e, r) {
                            var n = null == t ? 0 : t.length;
                            return n ? Bi(t, (e = n - (e = r || e === o ? 1 : Gs(e))) < 0 ? 0 : e, n) : []
                        }, pn.takeRightWhile = function (t, e) {
                            return t && t.length ? Gi(t, Eo(e, 3), !1, !0) : []
                        }, pn.takeWhile = function (t, e) {
                            return t && t.length ? Gi(t, Eo(e, 3)) : []
                        }, pn.tap = function (t, e) {
                            return e(t), t
                        }, pn.throttle = function (t, e, r) {
                            var n = !0,
                                i = !0;
                            if ("function" != typeof t) throw new ie(u);
                            return As(r) && (n = "leading" in r ? !!r.leading : n, i = "trailing" in r ? !!r.trailing : i), is(t, e, {
                                leading: n,
                                maxWait: e,
                                trailing: i
                            })
                        }, pn.thru = Fa, pn.toArray = Ns, pn.toPairs = cu, pn.toPairsIn = fu, pn.toPath = function (t) {
                            return _s(t) ? Je(t, ha) : Is(t) ? [t] : no(la(qs(t)))
                        }, pn.toPlainObject = Xs, pn.transform = function (t, e, r) {
                            var n = _s(t),
                                i = n || bs(t) || Rs(t);
                            if (e = Eo(e, 4), null == r) {
                                var o = t && t.constructor;
                                r = i ? n ? new o : [] : As(t) && ks(o) ? vn(Be(t)) : {}
                            }
                            return (i ? Ye : Vn)(t, function (t, n, i) {
                                return e(r, t, n, i)
                            }), r
                        }, pn.unary = function (t) {
                            return ts(t, 1)
                        }, pn.union = La, pn.unionBy = Pa, pn.unionWith = Ma, pn.uniq = function (t) {
                            return t && t.length ? ji(t) : []
                        }, pn.uniqBy = function (t, e) {
                            return t && t.length ? ji(t, Eo(e, 2)) : []
                        }, pn.uniqWith = function (t, e) {
                            return e = "function" == typeof e ? e : o, t && t.length ? ji(t, o, e) : []
                        }, pn.unset = function (t, e) {
                            return null == t || Ni(t, e)
                        }, pn.unzip = Ba, pn.unzipWith = Da, pn.update = function (t, e, r) {
                            return null == t ? t : Fi(t, e, Ui(r))
                        }, pn.updateWith = function (t, e, r, n) {
                            return n = "function" == typeof n ? n : o, null == t ? t : Fi(t, e, Ui(r), n)
                        }, pn.values = du, pn.valuesIn = function (t) {
                            return null == t ? [] : _r(t, ou(t))
                        }, pn.without = za, pn.words = ku, pn.wrap = function (t, e) {
                            return hs(Ui(e), t)
                        }, pn.xor = Oa, pn.xorBy = Ia, pn.xorWith = Ra, pn.zip = Ea, pn.zipObject = function (t, e) {
                            return Xi(t || [], e || [], Pn)
                        }, pn.zipObjectDeep = function (t, e) {
                            return Xi(t || [], e || [], Ai)
                        }, pn.zipWith = ja, pn.entries = cu, pn.entriesIn = fu, pn.extend = Ys, pn.extendWith = Vs, Ou(pn, pn), pn.add = Xu, pn.attempt = Tu, pn.camelCase = pu, pn.capitalize = vu, pn.ceil = qu, pn.clamp = function (t, e, r) {
                            return r === o && (r = e, e = o), r !== o && (r = (r = Hs(r)) == r ? r : 0), e !== o && (e = (e = Hs(e)) == e ? e : 0), In(Hs(t), e, r)
                        }, pn.clone = function (t) {
                            return Rn(t, p)
                        }, pn.cloneDeep = function (t) {
                            return Rn(t, f | p)
                        }, pn.cloneDeepWith = function (t, e) {
                            return Rn(t, f | p, e = "function" == typeof e ? e : o)
                        }, pn.cloneWith = function (t, e) {
                            return Rn(t, p, e = "function" == typeof e ? e : o)
                        }, pn.conformsTo = function (t, e) {
                            return null == e || En(t, e, iu(e))
                        }, pn.deburr = gu, pn.defaultTo = function (t, e) {
                            return null == t || t != t ? e : t
                        }, pn.divide = Uu, pn.endsWith = function (t, e, r) {
                            t = qs(t), e = Ei(e);
                            var n = t.length,
                                i = r = r === o ? n : In(Gs(r), 0, n);
                            return (r -= e.length) >= 0 && t.slice(r, i) == e
                        }, pn.eq = ds, pn.escape = function (t) {
                            return (t = qs(t)) && Tt.test(t) ? t.replace(St, wr) : t
                        }, pn.escapeRegExp = function (t) {
                            return (t = qs(t)) && zt.test(t) ? t.replace(Dt, "\\$&") : t
                        }, pn.every = function (t, e, r) {
                            var n = _s(t) ? $e : Wn;
                            return r && Vo(t, e, r) && (e = o), n(t, Eo(e, 3))
                        }, pn.find = Ha, pn.findIndex = ga, pn.findKey = function (t, e) {
                            return or(t, Eo(e, 3), Vn)
                        }, pn.findLast = Xa, pn.findLastIndex = _a, pn.findLastKey = function (t, e) {
                            return or(t, Eo(e, 3), $n)
                        }, pn.floor = Yu, pn.forEach = qa, pn.forEachRight = Ua, pn.forIn = function (t, e) {
                            return null == t ? t : Un(t, Eo(e, 3), ou)
                        }, pn.forInRight = function (t, e) {
                            return null == t ? t : Yn(t, Eo(e, 3), ou)
                        }, pn.forOwn = function (t, e) {
                            return t && Vn(t, Eo(e, 3))
                        }, pn.forOwnRight = function (t, e) {
                            return t && $n(t, Eo(e, 3))
                        }, pn.get = Js, pn.gt = ps, pn.gte = vs, pn.has = function (t, e) {
                            return null != t && Xo(t, e, ei)
                        }, pn.hasIn = tu, pn.head = ma, pn.identity = Mu, pn.includes = function (t, e, r, n) {
                            t = ms(t) ? t : du(t), r = r && !n ? Gs(r) : 0;
                            var i = t.length;
                            return r < 0 && (r = qr(i + r, 0)), Os(t) ? r <= i && t.indexOf(e, r) > -1 : !!i && sr(t, e, r) > -1
                        }, pn.indexOf = function (t, e, r) {
                            var n = null == t ? 0 : t.length;
                            if (!n) return -1;
                            var i = null == r ? 0 : Gs(r);
                            return i < 0 && (i = qr(n + i, 0)), sr(t, e, i)
                        }, pn.inRange = function (t, e, r) {
                            return e = Fs(e), r === o ? (r = e, e = 0) : r = Fs(r),
                                function (t, e, r) {
                                    return t >= Ur(e, r) && t < qr(e, r)
                                }(t = Hs(t), e, r)
                        }, pn.invoke = nu, pn.isArguments = gs, pn.isArray = _s, pn.isArrayBuffer = ys, pn.isArrayLike = ms, pn.isArrayLikeObject = xs, pn.isBoolean = function (t) {
                            return !0 === t || !1 === t || Ls(t) && Jn(t) == X
                        }, pn.isBuffer = bs, pn.isDate = ws, pn.isElement = function (t) {
                            return Ls(t) && 1 === t.nodeType && !Bs(t)
                        }, pn.isEmpty = function (t) {
                            if (null == t) return !0;
                            if (ms(t) && (_s(t) || "string" == typeof t || "function" == typeof t.splice || bs(t) || Rs(t) || gs(t))) return !t.length;
                            var e = Ho(t);
                            if (e == Z || e == rt) return !t.size;
                            if (Ko(t)) return !hi(t).length;
                            for (var r in t)
                                if (he.call(t, r)) return !1;
                            return !0
                        }, pn.isEqual = function (t, e) {
                            return ai(t, e)
                        }, pn.isEqualWith = function (t, e, r) {
                            var n = (r = "function" == typeof r ? r : o) ? r(t, e) : o;
                            return n === o ? ai(t, e, o, r) : !!n
                        }, pn.isError = Ss, pn.isFinite = function (t) {
                            return "number" == typeof t && Wr(t)
                        }, pn.isFunction = ks, pn.isInteger = Ts, pn.isLength = Cs, pn.isMap = Ps, pn.isMatch = function (t, e) {
                            return t === e || si(t, e, No(e))
                        }, pn.isMatchWith = function (t, e, r) {
                            return r = "function" == typeof r ? r : o, si(t, e, No(e), r)
                        }, pn.isNaN = function (t) {
                            return Ms(t) && t != +t
                        }, pn.isNative = function (t) {
                            if (Qo(t)) throw new Kt(s);
                            return ui(t)
                        }, pn.isNil = function (t) {
                            return null == t
                        }, pn.isNull = function (t) {
                            return null === t
                        }, pn.isNumber = Ms, pn.isObject = As, pn.isObjectLike = Ls, pn.isPlainObject = Bs, pn.isRegExp = Ds, pn.isSafeInteger = function (t) {
                            return Ts(t) && t >= -O && t <= O
                        }, pn.isSet = zs, pn.isString = Os, pn.isSymbol = Is, pn.isTypedArray = Rs, pn.isUndefined = function (t) {
                            return t === o
                        }, pn.isWeakMap = function (t) {
                            return Ls(t) && Ho(t) == at
                        }, pn.isWeakSet = function (t) {
                            return Ls(t) && Jn(t) == st
                        }, pn.join = function (t, e) {
                            return null == t ? "" : Hr.call(t, e)
                        }, pn.kebabCase = _u, pn.last = Sa, pn.lastIndexOf = function (t, e, r) {
                            var n = null == t ? 0 : t.length;
                            if (!n) return -1;
                            var i = n;
                            return r !== o && (i = (i = Gs(r)) < 0 ? qr(n + i, 0) : Ur(i, n - 1)), e == e ? function (t, e, r) {
                                for (var n = r + 1; n--;)
                                    if (t[n] === e) return n;
                                return n
                            }(t, e, i) : ar(t, lr, i, !0)
                        }, pn.lowerCase = yu, pn.lowerFirst = mu, pn.lt = Es, pn.lte = js, pn.max = function (t) {
                            return t && t.length ? Hn(t, Mu, ti) : o
                        }, pn.maxBy = function (t, e) {
                            return t && t.length ? Hn(t, Eo(e, 2), ti) : o
                        }, pn.mean = function (t) {
                            return hr(t, Mu)
                        }, pn.meanBy = function (t, e) {
                            return hr(t, Eo(e, 2))
                        }, pn.min = function (t) {
                            return t && t.length ? Hn(t, Mu, fi) : o
                        }, pn.minBy = function (t, e) {
                            return t && t.length ? Hn(t, Eo(e, 2), fi) : o
                        }, pn.stubArray = Wu, pn.stubFalse = Hu, pn.stubObject = function () {
                            return {}
                        }, pn.stubString = function () {
                            return ""
                        }, pn.stubTrue = function () {
                            return !0
                        }, pn.multiply = $u, pn.nth = function (t, e) {
                            return t && t.length ? _i(t, Gs(e)) : o
                        }, pn.noConflict = function () {
                            return ze._ === this && (ze._ = ve), this
                        }, pn.noop = Iu, pn.now = Ja, pn.pad = function (t, e, r) {
                            t = qs(t);
                            var n = (e = Gs(e)) ? Br(t) : 0;
                            if (!e || n >= e) return t;
                            var i = (e - n) / 2;
                            return mo(Nr(i), r) + t + mo(jr(i), r)
                        }, pn.padEnd = function (t, e, r) {
                            t = qs(t);
                            var n = (e = Gs(e)) ? Br(t) : 0;
                            return e && n < e ? t + mo(e - n, r) : t
                        }, pn.padStart = function (t, e, r) {
                            t = qs(t);
                            var n = (e = Gs(e)) ? Br(t) : 0;
                            return e && n < e ? mo(e - n, r) + t : t
                        }, pn.parseInt = function (t, e, r) {
                            return r || null == e ? e = 0 : e && (e = +e), Vr(qs(t).replace(It, ""), e || 0)
                        }, pn.random = function (t, e, r) {
                            if (r && "boolean" != typeof r && Vo(t, e, r) && (e = r = o), r === o && ("boolean" == typeof e ? (r = e, e = o) : "boolean" == typeof t && (r = t, t = o)), t === o && e === o ? (t = 0, e = 1) : (t = Fs(t), e === o ? (e = t, t = 0) : e = Fs(e)), t > e) {
                                var n = t;
                                t = e, e = n
                            }
                            if (r || t % 1 || e % 1) {
                                var i = $r();
                                return Ur(t + i * (e - t + Pe("1e-" + ((i + "").length - 1))), e)
                            }
                            return wi(t, e)
                        }, pn.reduce = function (t, e, r) {
                            var n = _s(t) ? er : dr,
                                i = arguments.length < 3;
                            return n(t, Eo(e, 4), r, i, Fn)
                        }, pn.reduceRight = function (t, e, r) {
                            var n = _s(t) ? rr : dr,
                                i = arguments.length < 3;
                            return n(t, Eo(e, 4), r, i, Gn)
                        }, pn.repeat = function (t, e, r) {
                            return e = (r ? Vo(t, e, r) : e === o) ? 1 : Gs(e), Si(qs(t), e)
                        }, pn.replace = function () {
                            var t = arguments,
                                e = qs(t[0]);
                            return t.length < 3 ? e : e.replace(t[1], t[2])
                        }, pn.result = function (t, e, r) {
                            var n = -1,
                                i = (e = Yi(e, t)).length;
                            for (i || (i = 1, t = o); ++n < i;) {
                                var a = null == t ? o : t[ha(e[n])];
                                a === o && (n = i, a = r), t = ks(a) ? a.call(t) : a
                            }
                            return t
                        }, pn.round = Zu, pn.runInContext = t, pn.sample = function (t) {
                            return (_s(t) ? Tn : Ti)(t)
                        }, pn.size = function (t) {
                            if (null == t) return 0;
                            if (ms(t)) return Os(t) ? Br(t) : t.length;
                            var e = Ho(t);
                            return e == Z || e == rt ? t.size : hi(t).length
                        }, pn.snakeCase = xu, pn.some = function (t, e, r) {
                            var n = _s(t) ? nr : Di;
                            return r && Vo(t, e, r) && (e = o), n(t, Eo(e, 3))
                        }, pn.sortedIndex = function (t, e) {
                            return zi(t, e)
                        }, pn.sortedIndexBy = function (t, e, r) {
                            return Oi(t, e, Eo(r, 2))
                        }, pn.sortedIndexOf = function (t, e) {
                            var r = null == t ? 0 : t.length;
                            if (r) {
                                var n = zi(t, e);
                                if (n < r && ds(t[n], e)) return n
                            }
                            return -1
                        }, pn.sortedLastIndex = function (t, e) {
                            return zi(t, e, !0)
                        }, pn.sortedLastIndexBy = function (t, e, r) {
                            return Oi(t, e, Eo(r, 2), !0)
                        }, pn.sortedLastIndexOf = function (t, e) {
                            if (null != t && t.length) {
                                var r = zi(t, e, !0) - 1;
                                if (ds(t[r], e)) return r
                            }
                            return -1
                        }, pn.startCase = bu, pn.startsWith = function (t, e, r) {
                            return t = qs(t), r = null == r ? 0 : In(Gs(r), 0, t.length), e = Ei(e), t.slice(r, r + e.length) == e
                        }, pn.subtract = Qu, pn.sum = function (t) {
                            return t && t.length ? pr(t, Mu) : 0
                        }, pn.sumBy = function (t, e) {
                            return t && t.length ? pr(t, Eo(e, 2)) : 0
                        }, pn.template = function (t, e, r) {
                            var n = pn.templateSettings;
                            r && Vo(t, e, r) && (e = o), t = qs(t), e = Vs({}, e, n, Ao);
                            var i, a, s = Vs({}, e.imports, n.imports, Ao),
                                u = iu(s),
                                l = _r(s, u),
                                h = 0,
                                c = e.interpolate || Zt,
                                f = "__p += '",
                                d = re((e.escape || Zt).source + "|" + c.source + "|" + (c === Lt ? Wt : Zt).source + "|" + (e.evaluate || Zt).source + "|$", "g"),
                                p = "//# sourceURL=" + ("sourceURL" in e ? e.sourceURL : "lodash.templateSources[" + ++Te + "]") + "\n";
                            t.replace(d, function (e, r, n, o, s, u) {
                                return n || (n = o), f += t.slice(h, u).replace(Qt, Sr), r && (i = !0, f += "' +\n__e(" + r + ") +\n'"), s && (a = !0, f += "';\n" + s + ";\n__p += '"), n && (f += "' +\n((__t = (" + n + ")) == null ? '' : __t) +\n'"), h = u + e.length, e
                            }), f += "';\n";
                            var v = e.variable;
                            v || (f = "with (obj) {\n" + f + "\n}\n"), f = (a ? f.replace(mt, "") : f).replace(xt, "$1").replace(bt, "$1;"), f = "function(" + (v || "obj") + ") {\n" + (v ? "" : "obj || (obj = {});\n") + "var __t, __p = ''" + (i ? ", __e = _.escape" : "") + (a ? ", __j = Array.prototype.join;\nfunction print() { __p += __j.call(arguments, '') }\n" : ";\n") + f + "return __p\n}";
                            var g = Tu(function () {
                                return Jt(u, p + "return " + f).apply(o, l)
                            });
                            if (g.source = f, Ss(g)) throw g;
                            return g
                        }, pn.times = function (t, e) {
                            if ((t = Gs(t)) < 1 || t > O) return [];
                            var r = E,
                                n = Ur(t, E);
                            e = Eo(e), t -= E;
                            for (var i = vr(n, e); ++r < t;) e(r);
                            return i
                        }, pn.toFinite = Fs, pn.toInteger = Gs, pn.toLength = Ws, pn.toLower = function (t) {
                            return qs(t).toLowerCase()
                        }, pn.toNumber = Hs, pn.toSafeInteger = function (t) {
                            return t ? In(Gs(t), -O, O) : 0 === t ? t : 0
                        }, pn.toString = qs, pn.toUpper = function (t) {
                            return qs(t).toUpperCase()
                        }, pn.trim = function (t, e, r) {
                            if ((t = qs(t)) && (r || e === o)) return t.replace(Ot, "");
                            if (!t || !(e = Ei(e))) return t;
                            var n = Dr(t),
                                i = Dr(e);
                            return $i(n, mr(n, i), xr(n, i) + 1).join("")
                        }, pn.trimEnd = function (t, e, r) {
                            if ((t = qs(t)) && (r || e === o)) return t.replace(Rt, "");
                            if (!t || !(e = Ei(e))) return t;
                            var n = Dr(t);
                            return $i(n, 0, xr(n, Dr(e)) + 1).join("")
                        }, pn.trimStart = function (t, e, r) {
                            if ((t = qs(t)) && (r || e === o)) return t.replace(It, "");
                            if (!t || !(e = Ei(e))) return t;
                            var n = Dr(t);
                            return $i(n, mr(n, Dr(e))).join("")
                        }, pn.truncate = function (t, e) {
                            var r = A,
                                n = L;
                            if (As(e)) {
                                var i = "separator" in e ? e.separator : i;
                                r = "length" in e ? Gs(e.length) : r, n = "omission" in e ? Ei(e.omission) : n
                            }
                            var a = (t = qs(t)).length;
                            if (kr(t)) {
                                var s = Dr(t);
                                a = s.length
                            }
                            if (r >= a) return t;
                            var u = r - Br(n);
                            if (u < 1) return n;
                            var l = s ? $i(s, 0, u).join("") : t.slice(0, u);
                            if (i === o) return l + n;
                            if (s && (u += l.length - u), Ds(i)) {
                                if (t.slice(u).search(i)) {
                                    var h, c = l;
                                    for (i.global || (i = re(i.source, qs(Ht.exec(i)) + "g")), i.lastIndex = 0; h = i.exec(c);) var f = h.index;
                                    l = l.slice(0, f === o ? u : f)
                                }
                            } else if (t.indexOf(Ei(i), u) != u) {
                                var d = l.lastIndexOf(i);
                                d > -1 && (l = l.slice(0, d))
                            }
                            return l + n
                        }, pn.unescape = function (t) {
                            return (t = qs(t)) && kt.test(t) ? t.replace(wt, zr) : t
                        }, pn.uniqueId = function (t) {
                            var e = ++ce;
                            return qs(t) + e
                        }, pn.upperCase = wu, pn.upperFirst = Su, pn.each = qa, pn.eachRight = Ua, pn.first = ma, Ou(pn, (Vu = {}, Vn(pn, function (t, e) {
                            he.call(pn.prototype, e) || (Vu[e] = t)
                        }), Vu), {
                            chain: !1
                        }), pn.VERSION = "4.17.5", Ye(["bind", "bindKey", "curry", "curryRight", "partial", "partialRight"], function (t) {
                            pn[t].placeholder = pn
                        }), Ye(["drop", "take"], function (t, e) {
                            yn.prototype[t] = function (r) {
                                r = r === o ? 1 : qr(Gs(r), 0);
                                var n = this.__filtered__ && !e ? new yn(this) : this.clone();
                                return n.__filtered__ ? n.__takeCount__ = Ur(r, n.__takeCount__) : n.__views__.push({
                                    size: Ur(r, E),
                                    type: t + (n.__dir__ < 0 ? "Right" : "")
                                }), n
                            }, yn.prototype[t + "Right"] = function (e) {
                                return this.reverse()[t](e).reverse()
                            }
                        }), Ye(["filter", "map", "takeWhile"], function (t, e) {
                            var r = e + 1,
                                n = r == B || 3 == r;
                            yn.prototype[t] = function (t) {
                                var e = this.clone();
                                return e.__iteratees__.push({
                                    iteratee: Eo(t, 3),
                                    type: r
                                }), e.__filtered__ = e.__filtered__ || n, e
                            }
                        }), Ye(["head", "last"], function (t, e) {
                            var r = "take" + (e ? "Right" : "");
                            yn.prototype[t] = function () {
                                return this[r](1).value()[0]
                            }
                        }), Ye(["initial", "tail"], function (t, e) {
                            var r = "drop" + (e ? "" : "Right");
                            yn.prototype[t] = function () {
                                return this.__filtered__ ? new yn(this) : this[r](1)
                            }
                        }), yn.prototype.compact = function () {
                            return this.filter(Mu)
                        }, yn.prototype.find = function (t) {
                            return this.filter(t).head()
                        }, yn.prototype.findLast = function (t) {
                            return this.reverse().find(t)
                        }, yn.prototype.invokeMap = ki(function (t, e) {
                            return "function" == typeof t ? new yn(this) : this.map(function (r) {
                                return ii(r, t, e)
                            })
                        }), yn.prototype.reject = function (t) {
                            return this.filter(us(Eo(t)))
                        }, yn.prototype.slice = function (t, e) {
                            t = Gs(t);
                            var r = this;
                            return r.__filtered__ && (t > 0 || e < 0) ? new yn(r) : (t < 0 ? r = r.takeRight(-t) : t && (r = r.drop(t)), e !== o && (r = (e = Gs(e)) < 0 ? r.dropRight(-e) : r.take(e - t)), r)
                        }, yn.prototype.takeRightWhile = function (t) {
                            return this.reverse().takeWhile(t).reverse()
                        }, yn.prototype.toArray = function () {
                            return this.take(E)
                        }, Vn(yn.prototype, function (t, e) {
                            var r = /^(?:filter|find|map|reject)|While$/.test(e),
                                n = /^(?:head|last)$/.test(e),
                                i = pn[n ? "take" + ("last" == e ? "Right" : "") : e],
                                a = n || /^find/.test(e);
                            i && (pn.prototype[e] = function () {
                                var e = this.__wrapped__,
                                    s = n ? [1] : arguments,
                                    u = e instanceof yn,
                                    l = s[0],
                                    h = u || _s(e),
                                    c = function (t) {
                                        var e = i.apply(pn, tr([t], s));
                                        return n && f ? e[0] : e
                                    };
                                h && r && "function" == typeof l && 1 != l.length && (u = h = !1);
                                var f = this.__chain__,
                                    d = !!this.__actions__.length,
                                    p = a && !f,
                                    v = u && !d;
                                if (!a && h) {
                                    e = v ? e : new yn(this);
                                    var g = t.apply(e, s);
                                    return g.__actions__.push({
                                        func: Fa,
                                        args: [c],
                                        thisArg: o
                                    }), new _n(g, f)
                                }
                                return p && v ? t.apply(this, s) : (g = this.thru(c), p ? n ? g.value()[0] : g.value() : g)
                            })
                        }), Ye(["pop", "push", "shift", "sort", "splice", "unshift"], function (t) {
                            var e = oe[t],
                                r = /^(?:push|sort|unshift)$/.test(t) ? "tap" : "thru",
                                n = /^(?:pop|shift)$/.test(t);
                            pn.prototype[t] = function () {
                                var t = arguments;
                                if (n && !this.__chain__) {
                                    var i = this.value();
                                    return e.apply(_s(i) ? i : [], t)
                                }
                                return this[r](function (r) {
                                    return e.apply(_s(r) ? r : [], t)
                                })
                            }
                        }), Vn(yn.prototype, function (t, e) {
                            var r = pn[e];
                            if (r) {
                                var n = r.name + "";
                                (on[n] || (on[n] = [])).push({
                                    name: e,
                                    func: r
                                })
                            }
                        }), on[vo(o, y).name] = [{
                            name: "wrapper",
                            func: o
                        }], yn.prototype.clone = function () {
                            var t = new yn(this.__wrapped__);
                            return t.__actions__ = no(this.__actions__), t.__dir__ = this.__dir__, t.__filtered__ = this.__filtered__, t.__iteratees__ = no(this.__iteratees__), t.__takeCount__ = this.__takeCount__, t.__views__ = no(this.__views__), t
                        }, yn.prototype.reverse = function () {
                            if (this.__filtered__) {
                                var t = new yn(this);
                                t.__dir__ = -1, t.__filtered__ = !0
                            } else(t = this.clone()).__dir__ *= -1;
                            return t
                        }, yn.prototype.value = function () {
                            var t = this.__wrapped__.value(),
                                e = this.__dir__,
                                r = _s(t),
                                n = e < 0,
                                i = r ? t.length : 0,
                                o = function (t, e, r) {
                                    for (var n = -1, i = r.length; ++n < i;) {
                                        var o = r[n],
                                            a = o.size;
                                        switch (o.type) {
                                        case "drop":
                                            t += a;
                                            break;
                                        case "dropRight":
                                            e -= a;
                                            break;
                                        case "take":
                                            e = Ur(e, t + a);
                                            break;
                                        case "takeRight":
                                            t = qr(t, e - a)
                                        }
                                    }
                                    return {
                                        start: t,
                                        end: e
                                    }
                                }(0, i, this.__views__),
                                a = o.start,
                                s = o.end,
                                u = s - a,
                                l = n ? s : a - 1,
                                h = this.__iteratees__,
                                c = h.length,
                                f = 0,
                                d = Ur(u, this.__takeCount__);
                            if (!r || !n && i == u && d == u) return Wi(t, this.__actions__);
                            var p = [];
                            t: for (; u-- && f < d;) {
                                for (var v = -1, g = t[l += e]; ++v < c;) {
                                    var _ = h[v],
                                        y = _.iteratee,
                                        m = _.type,
                                        x = y(g);
                                    if (m == D) g = x;
                                    else if (!x) {
                                        if (m == B) continue t;
                                        break t
                                    }
                                }
                                p[f++] = g
                            }
                            return p
                        }, pn.prototype.at = Ga, pn.prototype.chain = function () {
                            return Na(this)
                        }, pn.prototype.commit = function () {
                            return new _n(this.value(), this.__chain__)
                        }, pn.prototype.next = function () {
                            this.__values__ === o && (this.__values__ = Ns(this.value()));
                            var t = this.__index__ >= this.__values__.length;
                            return {
                                done: t,
                                value: t ? o : this.__values__[this.__index__++]
                            }
                        }, pn.prototype.plant = function (t) {
                            for (var e, r = this; r instanceof gn;) {
                                var n = fa(r);
                                n.__index__ = 0, n.__values__ = o, e ? i.__wrapped__ = n : e = n;
                                var i = n;
                                r = r.__wrapped__
                            }
                            return i.__wrapped__ = t, e
                        }, pn.prototype.reverse = function () {
                            var t = this.__wrapped__;
                            if (t instanceof yn) {
                                var e = t;
                                return this.__actions__.length && (e = new yn(this)), (e = e.reverse()).__actions__.push({
                                    func: Fa,
                                    args: [Aa],
                                    thisArg: o
                                }), new _n(e, this.__chain__)
                            }
                            return this.thru(Aa)
                        }, pn.prototype.toJSON = pn.prototype.valueOf = pn.prototype.value = function () {
                            return Wi(this.__wrapped__, this.__actions__)
                        }, pn.prototype.first = pn.prototype.head, je && (pn.prototype[je] = function () {
                            return this
                        }), pn
                    }();
                    ze._ = Or, (i = function () {
                        return Or
                    }.call(e, r, e, n)) === o || (n.exports = i)
                }).call(this)
            }).call(this, r(100), r(99)(t))
        },
        function (t, e, r) {
            Object.defineProperty(e, "__esModule", {
                value: !0
            });
            var n = function () {
                    function t(t, e) {
                        for (var r = 0; r < e.length; r++) {
                            var n = e[r];
                            n.enumerable = n.enumerable || !1, n.configurable = !0, "value" in n && (n.writable = !0), Object.defineProperty(t, n.key, n)
                        }
                    }
                    return function (e, r, n) {
                        return r && t(e.prototype, r), n && t(e, n), e
                    }
                }(),
                i = l(r(101)),
                o = l(r(39)),
                a = l(r(44)),
                s = l(r(41)),
                u = l(r(40));

            function l(t) {
                return t && t.__esModule ? t : {
                    default: t
                }
            }
            var h = {},
                c = {},
                f = function () {
                    function t(e, r, n) {
                        ! function (t, e) {
                            if (!(t instanceof e)) throw new TypeError("Cannot call a class as a function")
                        }(this, t), c = this, this.baseGroup = new o.default.Group({
                            name: "base"
                        }), h.crossGroup = "", h.crossTextGroup = "", h.preAddShipGroup = "", h.curShipGroupId = "", h.curBorderId = "", h.hdShipDragging = !1, h.savedShipPosition = {}, h.timeout = !1, h.xData = [], h.yData = [], h.xLength = 0, h.yLength = 0, h.hdLineDragging = !1, h.preAddShipData = {}, h.preAddShipCallback = !1, h.preAddShipSaveHandler = !1, h.zeroPosition = [], h.leftTopPosition = [], h.shipMouseoverFlag = !1;
                        var s = {
                            random: (0, a.default)(),
                            xAxis: {
                                interval: 25,
                                scale: 2,
                                height: 30
                            },
                            yAxis: {
                                interval: 8
                            },
                            shipOpt: {
                                height: 30,
                                prowWidth: 30,
                                circleR: 5
                            },
                            shipDbClick: !1,
                            shipSaveHandler: !1,
                            shipContextmenu: !1,
                            shipMouseover: !1,
                            shipMouseout: !1,
                            preAddOpt: {
                                flag: !1,
                                width: 100,
                                height: 50
                            }
                        };
                        this.opt = i.default.merge(s, r), h = {}, this.zrender = o.default.init(document.getElementById(e)), this.data = n
                    }
                    return n(t, [{
                        key: "draw",
                        value: function (t, e) {
                            c.data = t, e && e.xScale && (c.opt.xAxis.scale = e.xScale), this.baseGroup.removeAll(), this._setZrenderSize(t.berthAxisInfoList, t.timeAxisInfo), this._drawX(t.berthAxisInfoList), this._drawY(t.timeAxisInfo), this._drawCenter(), this._drawShips(t.shipList)
                        }
                    }, {
                        key: "addOneShip",
                        value: function (t, e) {
                            var r = new o.default.Group({
                                    name: t.shipVisitId
                                }),
                                n = new o.default.Group({
                                    name: "shipBox"
                                });
                            r.xScope = [];
                            var i = [];
                            if (i[0] = this._findXPositionByCode(t.planBerthCode, t.planBeginBollardCode), i[1] = this._findXPositionByCode(t.planBerthCode, t.planEndBollardCode), i[0] && i[1] && (t.shipLength || (t.shipLength = (i[1].pos - i[0].pos) * c.opt.xAxis.scale), e && this._resizeXPosByShipLength(i, t), t.etb && t.etu)) {
                                try {
                                    t.planBerthStartTimestamp = new Date(t.etb).getTime(), t.planBerthEndTimestamp = new Date(t.etu).getTime()
                                } catch (t) {
                                    return
                                }
                                if (!(t.planBerthStartTimestamp < h.yData[0].timestamp || t.planBerthEndTimestamp > h.yData[h.yData.length - 1].timestamp)) {
                                    r.xScope[0] = i[0].pos, r.xScope[1] = i[1].pos, r.yScope = [], r.yScope[0] = this._findYPositionByTime(t.planBerthStartTimestamp), r.yScope[1] = this._findYPositionByTime(t.planBerthEndTimestamp);
                                    var a = r.yScope[0] - r.yScope[1],
                                        l = r.xScope[1] - r.xScope[0];
                                    t.planBeginBollardName = h.xData[i[0].index].bollardName, t.planEndBollardName = h.xData[i[1].index].bollardName, n.add(this._drawShipText(t, l, a));
                                    var f = t.planBerthingMode;
                                    t.sortNo || (t.sortNo = "");
                                    var d = new u.default({
                                        name: "berthShip",
                                        draggable: !0,
                                        shape: {
                                            x: 0,
                                            y: 0,
                                            width: l,
                                            height: a,
                                            arrow: f
                                        },
                                        style: {
                                            fill: "#7fc3fc",
                                            stroke: "#272cff",
                                            opacity: 1,
                                            shadowBlur: 5,
                                            shadowColor: "#272cff",
                                            textLineHeight:16,
                                          /*  text: "{no|" + t.sortNo + "}{info|" + t.shipName + "\n船长:" + t.shipLongNum + "米 " + t.planBerthingModeName + "}",*/
                                            text: " {info|" + t.shipName + "\n船长:" + t.shipLongNum + "米 " + t.planBerthingModeName + "}",
                                            rich: {
/*                                                no: {
                                                    fontSize: 14,
                                                    textFill: "#d90000",
                                                    textAlign: "center"
                                                },*/
                                                info: {
                                                    fontSize: 14,
                                                    textFill: "#000"
                                                }
                                            }
                                        }
                                    });
                                    n.add(d);
                                    var p = new o.default.Circle({
                                        name: "beginCircle",
                                        shape: {
                                            cx: l,
                                            cy: a / 2,
                                            r: c.opt.shipOpt.circleR
                                        },
                                        style: {
                                            fill: "#fff",
                                            stroke: "#431eff"
                                        }
                                    });
                                    n.add(p);
                                    var v = new o.default.Circle({
                                        name: "endCircle",
                                        shape: {
                                            cx: 0,
                                            cy: a / 2,
                                            r: c.opt.shipOpt.circleR
                                        },
                                        style: {
                                            fill: "#fff",
                                            stroke: "#431eff"
                                        }
                                    });
                                    return n.add(v), n.add(this._drawChangeBorder(t, r)), n.add(this._drawChangeWidth(t, r)), r.add(n), r.on("mousedown", function (t) {
                                        1 === t.which ? (h.hdShipDragging = !0, d.draggable = !0, c._saveCurPosition()) : (h.hdShipDragging = !1, d.draggable = !1)
                                    }), (0, s.default)(window).on("mouseup", {
                                        group: r
                                    }, function (t) {
                                        !h.hdLineDragging && h.hdShipDragging && h.curShipGroupId === t.data.group.id && t.data.group.trigger("mouseup"), h.curShipGroupId || (h.hdShipDragging = !1)
                                    }), r.on("mouseup", function (e) {
                                        if (h.crossGroup && h.hdShipDragging && h.curShipGroupId === this.id) {
                                            h.crossTextGroup.removeAll(), h.hdShipDragging = !1, h.curShipGroupId = "";
                                            var r = !1,
                                                n = !1;
                                            h.crossGroup.eachChild(function (t) {
                                                t.hdOverBorder ? r = !0 : h.savedShipPosition[t.name] !== t.hdindex && (n = !0)
                                            }), r || !n ? c._freshShipPosition(this) : (c._updateShipXY(t, this), c._freshShipPosition(this), "function" == typeof c.opt.shipSaveHandler && c.opt.shipSaveHandler(t))
                                        }
                                    }), r.on("mousemove", function (e) {
                                        if (!h.crossGroup || h.hdShipDragging || h.hdLineDragging || h.curShipGroupId && h.curShipGroupId === this.id || (c._shipCrossLines(t, e, this), "function" == typeof c.opt.shipMouseover && (c.opt.shipMouseover(e, t), h.shipMouseoverFlag = !0)), h.hdLineDragging) {
                                            h.crossGroup.removeAll();
                                            var r = c._getXCrossLine(e.offsetX);
                                            h.crossGroup.add(r);
                                            var n = c._getYCrossLine(e.offsetY);
                                            h.crossGroup.add(n)
                                        }
                                    }), r.on("drag", function (e) {
                                        c._hideShipMouseOver(), this.childOfName("shipBox").eachChild(function (t) {
                                            t.position = [e.target.position[0], e.target.position[1]], t.dirty()
                                        }), c._shipCrossLines(t, e, this), c._shipCrossText(t, e, this)
                                    }), r.on("dblclick", function (e) {
                                        c.opt.shipDbClick && c.opt.shipDbClick.call(this, t, e), c._hideShipMouseOver()
                                    }), r.on("contextmenu", function (e) {
                                        e.event.preventDefault(), "function" == typeof c.opt.shipContextmenu && c.opt.shipContextmenu(e, t), c._hideShipMouseOver()
                                    }), r.position = [r.xScope[0], r.yScope[1]], r.hddata = {}, r.hddata.ship = t, this.baseGroup.add(r), e && c.data.shipList.push(t), !0
                                }
                            }
                        }
                    }, {
                        key: "_saveCurPosition",
                        value: function () {
                            h.savedShipPosition = {};
                            var t = h.crossGroup.children();
                            if (4 === t.length)
                                for (var e = 0; e < t.length; e++) {
                                    var r = t[e];
                                    h.savedShipPosition[r.name] = r.hdindex
                                }
                        }
                    }, {
                        key: "refreshOneShip",
                        value: function (t) {
                            c.baseGroup.remove(c.baseGroup.childOfName(t.shipVisitId)), c.addOneShip(t)
                        }
                    }, {
                        key: "removeOneShip",
                        value: function (t) {
                            c.baseGroup.remove(c.baseGroup.childOfName(t));
                            for (var e = -1, r = 0; r < c.data.shipList.length; r++) {
                                c.data.shipList[r].shipVisitId === t && (e = r)
                            } - 1 !== e && c.data.shipList.splice(e, 1)
                        }
                    }, {
                        key: "preAddShip",
                        value: function (t, e, r) {
                            c.opt.preAddOpt.flag = !0, h.preAddShipData = t, h.preAddShipCallback = e, h.preAddShipSaveHandler = r;
                            var n = t.shipLength;
                            n && (c.opt.preAddOpt.width = n / c.opt.xAxis.scale)
                        }
                    }, {
                        key: "_hideShipMouseOver",
                        value: function (t) {
                            h.shipMouseoverFlag && ("function" == typeof c.opt.shipMouseout && c.opt.shipMouseout(t), h.shipMouseoverFlag = !1)
                        }
                    }, {
                        key: "_setZrenderSize",
                        value: function (t, e) {
                            var r = (e.times.length - 1) * c.opt.yAxis.interval;
                            r = r + 40 + c.opt.xAxis.height;
                            for (var n = 0, i = 0; i < t.length; i++) {
                                var o = t[t.length - 1 - i];
                                if (o.bollardList.length > 0) {
                                    n = o.bollardList[o.bollardList.length - 1].bollardDistance;
                                    break
                                }
                            }
                            n /= c.opt.xAxis.scale, h.xLength = n, n += 150, this.zrender.resize({
                                height: r,
                                width: n
                            }), h.zeroPosition = [130, r - 30 - c.opt.xAxis.height]
                        }
                    }, {
                        key: "_drawX",
                        value: function (t) {
                            var e = new o.default.Group({
                                name: "xAxis"
                            });
                            h.xData = [];
                            var r = h.xLength,
                                n = new o.default.Line({
                                    shape: {
                                        x1: 0,
                                        y1: 0,
                                        x2: r,
                                        y2: 0
                                    },
                                    style: {
                                        lineWidth: 5,
                                        text: "",
                                        textPosition: "left",
                                        textOffset: [0, 20]
                                    }
                                });
                            e.add(n);
                            for (var i = 0, a = 0; a < t.length; a++) {
                                var s = t[a];
                                if (s.bollardList && 0 !== s.bollardList.length) {
                                    h.xData = h.xData.concat(s.bollardList);
                                    var u = new o.default.Group({
                                        name: "berth"
                                    });
                                    s.berthDiffSpace;
                                    var l = new o.default.Rect({
                                        shape: {
                                            x: i,
                                            y: 0,
                                            width: s.bollardList[s.bollardList.length - 1].bollardDistance / c.opt.xAxis.scale - i,
                                            height: c.opt.xAxis.height
                                        },
                                        style: {
                                            text: s.berth.berthName + " " + s.waterDepth,
                                            textPosition: "bottom",
                                            textVerticalAlign: "top",
                                            fill: "#fff",
                                            stroke: "#272cff"
                                        }
                                    });
                                    if (u.add(l), "N2" === s.berthName) {
                                        var f = new o.default.Line({
                                            shape: {
                                                x1: s.bollardList[s.bollardList.length - 1].bollardDistance / c.opt.xAxis.scale,
                                                y1: 0,
                                                x2: s.bollardList[s.bollardList.length - 1].bollardDistance / c.opt.xAxis.scale,
                                                y2: -h.yLength
                                            },
                                            style: {
                                                textPadding: 2,
                                                fontSize: 16,
                                                fontWeight: "bold",
                                                textFill: "#000000",
                                                textPosition: "left",
                                                lineDash: [2.5, 2],
                                                textBackgroundColor: "#555555"
                                            },
                                            zlevel: 1
                                        });
                                        u.add(f)
                                    }
                                    e.add(u);
                                    for (var d = 0; d < s.bollardList.length; d++) {
                                        var p = s.bollardList[d],
                                            v = new o.default.Line({
                                                shape: {
                                                    x1: p.bollardDistance / c.opt.xAxis.scale,
                                                    y1: 0,
                                                    x2: p.bollardDistance / c.opt.xAxis.scale,
                                                    y2: 10
                                                },
                                                style: {
                                                    text: p.bollardName,
                                                    textPosition: "bottom",
                                                    textVerticalAlign: "middle",
                                                    textBackgroundColor: "#ffffff"
                                                },
                                                z: 5
                                            });
                                        e.add(v)
                                    }
                                    i = s.bollardList[s.bollardList.length - 1].bollardDistance / c.opt.xAxis.scale
                                }
                            }
                            e.position = h.zeroPosition, this.baseGroup.add(e), this.zrender.add(this.baseGroup)
                        }
                    }, {
                        key: "_drawY",
                        value: function (t) {
                            var e = t.times;
                            h.intervalMills = t.intervalMills, h.yData = e;
                            var r = new o.default.Group,
                                n = (e.length - 1) * c.opt.yAxis.interval;
                            h.yLength = n;
                            var i = new o.default.Line({
                                shape: {
                                    x1: 0,
                                    y1: 0,
                                    x2: 0,
                                    y2: -n
                                },
                                style: {
                                    lineWidth: 3
                                }
                            });
                            r.add(i);
                            for (var a = new o.default.Group, s = 0; s < e.length; s++) {
                                var u = e[s],
                                    l = 20;
                                1 === u.level && (l = 30);
                                var f = {
                                    shape: {
                                        x1: 0,
                                        y1: -s * c.opt.yAxis.interval,
                                        x2: -l,
                                        y2: -s * c.opt.yAxis.interval
                                    },
                                    style: {
                                        textPosition: "left"
                                    }
                                };
                                s % 2 == 0 && (f.style.text = u.name);
                                var d = new o.default.Line(f);
                                a.add(d)
                            }
                            r.add(a), r.position = h.zeroPosition, this.baseGroup.add(r)
                        }
                    }, {
                        key: "_drawCenter",
                        value: function () {
                            var t = new o.default.Group({
                                name: "centerGroup"
                            });
                            h.crossGroup = new o.default.Group({
                                name: "crossGroup"
                            }), h.preAddShipGroup = new o.default.Group({
                                name: "preAddShipGroup"
                            }), h.crossTextGroup = new o.default.Group({
                                name: "crossTextGroup"
                            }), h.leftTopPosition = [h.zeroPosition[0], h.zeroPosition[1] - h.yLength];
                            var e = new o.default.Rect({
                                name: "centerRect",
                                shape: {
                                    x: 0,
                                    y: -h.yLength,
                                    width: h.xLength,
                                    height: h.yLength
                                },
                                style: {
                                    fill: "#f7f7f7"
                                }
                            });
                            t.add(e), t.on("mousemove", function (t) {
                                if (h.crossGroup) {
                                    h.hdShipDragging || (h.curShipGroupId = ""), h.crossGroup.removeAll();
                                    var e = c._getXCrossLine(t.offsetX);
                                    h.crossGroup.add(e);
                                    var r = c._getYCrossLine(t.offsetY);
                                    h.crossGroup.add(r)
                                }
                                c._drawPreAddShip(t), c._hideShipMouseOver()
                            }), (0, s.default)(window).on("mouseover", function () {
                                c._hideShipMouseOver()
                            }), t.on("click", function (t) {
                                if (c.opt.preAddOpt.flag) {
                                    var e = [];
                                    e[0] = c._getXDataByPos(t.offsetX), e[1] = c._getXDataByPos(t.offsetX + c.opt.preAddOpt.width);
                                    var r = [];
                                    if (r[0] = c._getYDataByPos(t.offsetY), r[1] = c._getYDataByPos(t.offsetY - c.opt.preAddOpt.height), e[0] && e[1] && r[0] && r[1]) {
                                        c.opt.preAddOpt.flag = !1, h.preAddShipGroup.removeAll(), h.preAddShipGroup.dirty();
                                        var n = h.preAddShipData;
                                        n.planBerthCode = e[0].berthCode, n.planBeginBollardCode = e[0].bollardCode, n.planBeginBollardName = e[0].bollardName, n.planEndBollardCode = e[1].bollardCode, n.planEndBollardName = e[1].bollardName, n.planBerthStartTimestamp = r[0].timestamp, n.etb = r[0].code, n.planBerthEndTimestamp = r[1].timestamp, n.etu = r[1].code, c.addOneShip(n, !0), "function" == typeof h.preAddShipSaveHandler && h.preAddShipSaveHandler(h.preAddShipData, h.preAddShipCallback)
                                    }
                                }
                            }), t.on("contextmenu", function (t) {
                                t.event.preventDefault(), c.opt.preAddOpt.flag && (c.opt.preAddOpt.flag = !1, h.preAddShipGroup.removeAll(), h.preAddShipGroup.dirty(), "function" == typeof h.preAddShipCallback && h.preAddShipCallback(!1))
                            }), t.position = h.zeroPosition, this.baseGroup.add(t), this.baseGroup.add(h.crossGroup), this.baseGroup.add(h.crossTextGroup), this.baseGroup.add(h.preAddShipGroup)
                        }
                    }, {
                        key: "_drawPreAddShip",
                        value: function (t) {
                            if (c.opt.preAddOpt.flag && h.preAddShipGroup) {
                                h.preAddShipGroup.removeAll();
                                var e = c._getCurrentXYData(),
                                    r = c.opt.preAddOpt.width,
                                    n = c.opt.preAddOpt.height;
                                h.preAddShipData.sortNo || (h.preAddShipData.sortNo = "");
                                var i = new u.default({
                                    name: "berthShip",
                                    draggable: !1,
                                    shape: {
                                        x: t.offsetX + 0,
                                        y: t.offsetY - n - 0,
                                        width: r,
                                        height: n,
                                        arrow: h.preAddShipData.planBerthingMode
                                    },
                                    style: {
                                        fill: "#7fc3fc",
                                        stroke: "#272cff",
                                        opacity: 1,
                                        shadowBlur: 5,
                                        shadowColor: "#272cff",
                                        text: "{no|" + h.preAddShipData.sortNo + "}\n{info|" + h.preAddShipData.shipName + "船长:" + h.preAddShipData.shipLongNum + "米 " + h.preAddShipData.planBerthingModeName + "}",
                                        rich: {
                                            no: {
                                                fontSize: 25,
                                                textFill: "#d90000",
                                                textAlign: "center"
                                            },
                                            info: {
                                                fontSize: 16,
                                                textFill: "#000",
                                                textAlign: "right"
                                            }
                                        }
                                    },
                                    zlevel: 3
                                });
                                h.preAddShipGroup.add(i);
                                var a = new o.default.Circle({
                                    name: "beginCircle",
                                    shape: {
                                        cx: t.offsetX,
                                        cy: t.offsetY - n / 2 - 0,
                                        r: c.opt.shipOpt.circleR
                                    },
                                    style: {
                                        text: e.x.bollardName,
                                        textBackgroundColor: "#fff",
                                        textPosition: "left",
                                        fill: "#fff",
                                        stroke: "#431eff"
                                    },
                                    zlevel: 3
                                });
                                h.preAddShipGroup.add(a);
                                var s = new o.default.Text({
                                        style: {
                                            text: e.y.name,
                                            textBackgroundColor: "#fff"
                                        },
                                        zlevel: 3
                                    }),
                                    l = s.getBoundingRect();
                                s.position = [t.offsetX + (r - l.width) / 2, t.offsetY + 2], h.preAddShipGroup.add(s), h.preAddShipGroup.silent = !0
                            }
                        }
                    }, {
                        key: "_drawShips",
                        value: function (t) {
                            for (var e = 0; e < t.length; e++) {
                                var r = t[e];
                                this.addOneShip(r)
                            }
                        }
                    }, {
                        key: "_getCurrentXYData",
                        value: function () {
                            var t = null,
                                e = h.crossGroup.children();
                            if (2 === e.length) {
                                t = {};
                                for (var r = 0; r < e.length; r++) {
                                    var n = e[r];
                                    t[n.hdtype] = n.hddata
                                }
                            }
                            return t
                        }
                    }, {
                        key: "_getYDataByPos",
                        value: function (t) {
                            var e = h.zeroPosition[1] - t,
                                r = Math.floor((e - 0) / c.opt.yAxis.interval);
                            return h.yData[r]
                        }
                    }, {
                        key: "_getYCrossLine",
                        value: function (t) {
                            var e = h.zeroPosition[1] - t,
                                r = Math.floor((e - 0) / c.opt.yAxis.interval),
                                n = "";
                            h.yData[r] && (n = h.yData[r].code.substr(5));
                            var i = new o.default.Line({
                                shape: {
                                    x1: h.leftTopPosition[0],
                                    y1: t,
                                    x2: h.leftTopPosition[0] + h.xLength,
                                    y2: t
                                },
                                silent: !0,
                                style: {
                                    text: n,
                                    textPadding: 2,
                                    fontSize: 16,
                                    fontWeight: "bold",
                                    textFill: "#ffffff",
                                    textPosition: "left",
                                    lineDash: [2.5, 2],
                                    textBackgroundColor: "#555555"
                                },
                                zlevel: 1
                            });
                            return i.hddata = h.yData[r], i.hdpos = t, i.hdtype = "y", i.hdindex = r, n || (i.hdOverBorder = !0), i
                        }
                    }, {
                        key: "_getXDataByPos",
                        value: function (t) {
                            var e = c._getXDataIndexByPos(t);
                            return h.xData[e]
                        }
                    }, {
                        key: "_getXCrossLine",
                        value: function (t) {
                            var e = c._getXDataIndexByPos(t),
                                r = "";
                            h.xData[e] && (r = h.xData[e].bollardName);
                            var n = new o.default.Line({
                                shape: {
                                    x1: t,
                                    y1: h.leftTopPosition[1],
                                    x2: t,
                                    y2: h.yLength + h.leftTopPosition[1]
                                },
                                silent: !0,
                                style: {
                                    text: r,
                                    textPadding: 2,
                                    fontSize: 16,
                                    fontWeight: "bold",
                                    textFill: "#ffffff",
                                    textPosition: "bottom",
                                    lineDash: [2.5, 2],
                                    textBackgroundColor: "#555555"
                                },
                                zlevel: 1
                            });
                            return n.hddata = h.xData[e], n.hdpos = t, n.hdtype = "x", n.hdindex = e, r || (n.hdOverBorder = !0), n
                        }
                    }, {
                        key: "_getXDataIndexByPos",
                        value: function (t) {
                            var e = t - h.zeroPosition[0] - 1;
                            if (e < 0) return -1;
                            for (var r = e * c.opt.xAxis.scale, n = void 0, i = 0; i < h.xData.length; i++) {
                                if (r <= h.xData[i].bollardDistance) {
                                    n = i;
                                    break
                                }
                            }
                            return n
                        }
                    }, {
                        key: "_findYPositionByTime",
                        value: function (t) {
                            var e = t - h.yData[0].timestamp,
                                r = c.opt.yAxis.interval * e / h.intervalMills;
                            return r = h.zeroPosition[1] - r
                        }
                    }, {
                        key: "_findXPositionByCode",
                        value: function (t, e) {
                            var r = {},
                                n = h.xData.findIndex(function (t) {
                                    return t.bollardCode === e
                                });
                            return -1 !== n ? (r.index = n, r.pos = h.xData[n].bollardDistance / c.opt.xAxis.scale + h.zeroPosition[0]) : r = !1, r
                        }
                    }, {
                        key: "_shipCrossText",
                        value: function (t, e, r) {
                            if (h.crossTextGroup) {
                                h.crossTextGroup.removeAll();
                                for (var n = c._getBoundRectByCrossLines(), i = h.crossGroup.children(), a = {
                                    style: {
                                        fontSize: 16,
                                        textFill: "#d90000",
                                        textBackgroundColor: "#fff"
                                    },
                                    zlevel: 1
                                }, s = 0; s < i.length; s++) {
                                    var u = i[s];
                                    if ("left" === u.name) {
                                        if (u.hddata) {
                                            a.style.text = u.hddata.bollardName;
                                            var l = new o.default.Text(a),
                                                f = l.getBoundingRect();
                                            l.position = [n.x - f.width - 20, n.y + (n.height - f.height) / 2], h.crossTextGroup.add(l)
                                        }
                                    } else if ("right" === u.name) {
                                        if (u.hddata) {
                                            a.style.text = u.hddata.bollardName;
                                            var d = new o.default.Text(a),
                                                p = d.getBoundingRect();
                                            d.position = [n.x + n.width + 20, n.y + (n.height - p.height) / 2], h.crossTextGroup.add(d)
                                        }
                                    } else if ("bottom" === u.name) {
                                        if (u.hddata) {
                                            a.style.text = u.hddata.code;
                                            var v = new o.default.Text(a),
                                                g = v.getBoundingRect();
                                            v.position = [n.x + (n.width - g.width) / 2, n.y + n.height + 20], h.crossTextGroup.add(v)
                                        }
                                    } else if ("top" === u.name && u.hddata) {
                                        a.style.text = u.hddata.code;
                                        var _ = new o.default.Text(a),
                                            y = _.getBoundingRect();
                                        _.position = [n.x + (n.width - y.width) / 2, n.y - y.height - 20], h.crossTextGroup.add(_)
                                    }
                                }
                            }
                        }
                    }, {
                        key: "_getBoundRectByCrossLines",
                        value: function () {
                            for (var t = h.crossGroup.children(), e = {}, r = 0; r < t.length; r++) {
                                var n = t[r];
                                e[n.name] = n.hdpos
                            }
                            return e.x = e.left, e.width = e.right - e.left, e.y = e.top, e.height = e.bottom - e.top, e
                        }
                    }, {
                        key: "_freshShipPosition",
                        value: function (t, e) {
                            var r = t.yScope[0] - t.yScope[1],
                                n = t.childOfName("shipBox");
                            n.eachChild(function (t) {
                                t.position = [0, 0]
                            });
                            var i = n.childOfName("berthShip"),
                                o = n.childOfName("shipText"),
                                a = n.childOfName("border"),
                                s = n.childOfName("beginCircle"),
                                u = n.childOfName("endCircle");
                            (e || 0 === e) && (i.attr("shape", {
                                y: 0,
                                height: r
                            }), o.eachChild(function (t) {
                                t.invisible = !1, "bottom" === t.name ? t.position[1] = t.position[1] + e : "top" !== t.name && (t.position[1] = t.position[1] + e / 2)
                            }), a.eachChild(function (t) {
                                t.position = [0, 0]
                            }), s.attr("shape", {
                                cy: r / 2
                            }), u.attr("shape", {
                                cy: r / 2
                            })), t.position = [t.xScope[0], t.yScope[1]], t.dirty()
                        }
                    }, {
                        key: "_freshShipWidthPosition",
                        value: function (t, e) {
                            t.xScope[1], t.xScope[0];
                            var r = t.childOfName("shipBox");
                            r.eachChild(function (t) {
                                t.position = [0, 0]
                            });
                            r.childOfName("berthShip");
                            var n = r.childOfName("shipText"),
                                i = r.childOfName("border");
                            r.childOfName("beginCircle"), r.childOfName("endCircle");
                            (e || 0 === e) && (n.eachChild(function (t) {
                                t.invisible = !1
                            }), i.eachChild(function (t) {
                                t.position = [0, 0]
                            })), t.position = [t.xScope[0], t.yScope[1]], t.dirty()
                        }
                    }, {
                        key: "_updateShipXY",
                        value: function (t, e) {
                            if (h.crossGroup) {
                                var r = h.crossGroup.children(),
                                    n = [];
                                if (4 === r.length) {
                                    for (var i = 0; i < r.length; i++) {
                                        var o = r[i];
                                        "left" === o.name ? o.hddata && (t.planBeginBollardCode = o.hddata.bollardCode, t.planBeginBollardName = o.hddata.bollardName, n[0] = o.hddata.berthCode, e.xScope[0] = o.hdpos) : "right" === o.name ? o.hddata && (t.planEndBollardCode = o.hddata.bollardCode, t.planEndBollardName = o.hddata.bollardName, n[1] = o.hddata.berthCode, e.xScope[1] = o.hdpos) : "bottom" === o.name ? o.hddata && (t.planBerthStartTimestamp = o.hddata.timestamp, t.etb = o.hddata.code, e.yScope[0] = o.hdpos) : "top" === o.name && o.hddata && (t.planBerthEndTimestamp = o.hddata.timestamp, t.etu = o.hddata.code, e.yScope[1] = o.hdpos)
                                    }
                                    t.planBerthCodeEditFlag && n[0] !== n[1] || (t.planBerthCode = n[0], t.planBerthCodeEditFlag = !1)
                                }
                                e.childOfName("shipBox").childOfName("shipText").eachChild(function (e) {
                                    "top" === e.name ? e.attr("style", {
                                        text: t.etu
                                    }) : "bottom" === e.name ? e.attr("style", {
                                        text: t.etb
                                    }) : "left" === e.name ? e.attr("style", {
                                        text: t.planBeginBollardName
                                    }) : "right" === e.name && e.attr("style", {
                                        text: t.planEndBollardName
                                    })
                                })
                            }
                        }
                    }, {
                        key: "_shipCrossLines",
                        value: function (t, e, r) {
                            if (h.crossGroup) {
                                h.curShipGroupId = r.id, h.crossGroup.removeAll();
                                var n = c._getXCrossLine(r.xScope[0] + e.target.position[0]);
                                n.name = "left", h.crossGroup.add(n);
                                var i = c._getXCrossLine(r.xScope[1] + e.target.position[0]);
                                i.name = "right", h.crossGroup.add(i);
                                var o = c._getYCrossLine(r.yScope[0] + e.target.position[1]);
                                o.name = "bottom", h.crossGroup.add(o);
                                var a = c._getYCrossLine(r.yScope[1] + e.target.position[1]);
                                a.name = "top", h.crossGroup.add(a)
                            }
                        }
                    }, {
                        key: "_drawShipText",
                        value: function (t, e, r) {
                            var n = new o.default.Group({
                                    name: "shipText"
                                }),
                                i = r,
                                a = r - i,
                                s = {
                                    style: {
                                        textBackgroundColor: "#fff"
                                    },
                                    z: 10,
                                    name: "top"
                                };
                            s.style.text = t.etu;
                            var u = new o.default.Text(s),
                                l = u.getBoundingRect();
                            return u.position = [(e - l.width) / 2, a - l.height - 5], n.add(u), s.name = "bottom", s.style.text = t.etb, l = (u = new o.default.Text(s)).getBoundingRect(), u.position = [(e - l.width) / 2, r + 2], n.add(u), s.name = "left", s.style.text = t.planBeginBollardName, l = (u = new o.default.Text(s)).getBoundingRect(), u.position = [-5 - l.width, a + (i - l.height) / 2], n.add(u), s.name = "right", s.style.text = t.planEndBollardName, l = (u = new o.default.Text(s)).getBoundingRect(), u.position = [e + 5, a + (i - l.height) / 2], n.add(u), n.silent = !0, n
                        }
                    }, {
                        key: "_drawChangeBorder",
                        value: function (t, e) {
                            e.yScope[0], e.yScope[1];
                            var r = e.xScope[1] - e.xScope[0],
                                n = new o.default.Group({
                                    name: "border"
                                }),
                                i = new o.default.Line({
                                    name: "lineTop",
                                    invisible: !0,
                                    draggable: !0,
                                    shape: {
                                        x1: 0,
                                        y1: 0,
                                        x2: r,
                                        y2: 0
                                    },
                                    style: {
                                        lineWidth: 5
                                    },
                                    z: 20,
                                    cursor: "n-resize"
                                });
                            return i.on("drag", function (t) {
                                t.cancelBubble = !0;
                                var r = e.yScope[0] - e.yScope[1],
                                    n = h.zeroPosition[1] - h.yLength >= t.target.position[1] + e.position[1],
                                    i = t.target.position[1] >= r - c.opt.shipOpt.height;
                                if (!n && !i) {
                                    var o = e.childOfName("shipBox");
                                    o.childOfName("berthShip").attr("shape", {
                                        y: t.target.position[1],
                                        height: r - t.target.position[1]
                                    }), o.childOfName("beginCircle").attr("shape", {
                                        cy: (r + t.target.position[1]) / 2
                                    }), o.childOfName("endCircle").attr("shape", {
                                        cy: (r + t.target.position[1]) / 2
                                    }), o.childOfName("shipText").eachChild(function (t) {
                                        "bottom" !== t.name && (t.invisible = !0)
                                    })
                                }
                                if (i) {
                                    h.crossGroup.removeAll();
                                    var a = c._getXCrossLine(t.offsetX);
                                    h.crossGroup.add(a);
                                    var s = c._getYCrossLine(e.position[1] + r - c.opt.shipOpt.height);
                                    h.crossGroup.add(s)
                                }
                            }), i.on("mousemove", function (t) {
                                t.cancelBubble = !0
                            }), i.on("mousedown", function (t) {
                                1 === t.which ? (h.hdLineDragging = !0, h.hdShipDragging = !1, h.curBorderId = this.id, i.draggable = !0) : (h.hdLineDragging = !1, h.hdShipDragging = !1, h.curBorderId = "", i.draggable = !1)
                            }), (0, s.default)(window).on("mouseup", {
                                lineTop: i
                            }, function (t) {
                                h.hdLineDragging && h.curBorderId === t.data.lineTop.id && t.data.lineTop.trigger("mouseup"), h.curBorderId || (h.hdLineDragging = !1)
                            }), i.on("mouseup", function (r) {
                                if (h.crossGroup && h.hdLineDragging && h.curBorderId === this.id) {
                                    h.hdLineDragging = !1, h.hdShipDragging = !1, h.curShipGroupId = "", this.position = [0, 0], h.curBorderId = "";
                                    var n = h.crossGroup.childCount();
                                    if (n > 0) {
                                        var i = !1,
                                            o = 0,
                                            a = {};
                                        if (h.crossGroup.eachChild(function (t) {
                                            if (t.hdOverBorder) return i = !0, !1;
                                            "y" === t.hdtype && t.hddata && (n > 2 ? "top" === t.name && (a = t) : a = t)
                                        }), a && (o = a.hdpos), i || o + c.opt.shipOpt.height > e.yScope[0]) c._freshShipPosition(e, 0), c._updateShipXY(t, e);
                                        else if (a) {
                                            var s = e.yScope[1] - a.hdpos;
                                            t.planBerthEndTimestamp = a.hddata.timestamp, t.etu = a.hddata.code, e.yScope[1] = a.hdpos, c._freshShipPosition(e, s), c._updateShipXY(t, e), "function" == typeof c.opt.shipSaveHandler && c.opt.shipSaveHandler(t)
                                        }
                                    }
                                }
                            }), n.add(i), n
                        }
                    }, {
                        key: "_drawChangeWidth",
                        value: function (t, e) {
                            var r = e.yScope[0] - e.yScope[1],
                                n = e.xScope[1] - e.xScope[0],
                                i = new o.default.Group({
                                    name: "border"
                                }),
                                a = new o.default.Line({
                                    name: "lineTop",
                                    invisible: !0,
                                    draggable: !0,
                                    shape: {
                                        x1: n,
                                        y1: 0,
                                        x2: n,
                                        y2: r
                                    },
                                    style: {
                                        lineWidth: 5
                                    },
                                    z: 20,
                                    cursor: "e-resize"
                                });
                            return a.on("drag", function (t) {
                                t.cancelBubble = !0;
                                var r = e.xScope[1] - e.xScope[0],
                                    n = h.zeroPosition[0] + h.xLength <= t.target.position[0] + e.position[0],
                                    i = t.target.position[0] <= r + c.opt.shipOpt.width;
                                if (!n && !i) {
                                    var o = e.childOfName("shipBox");
                                    o.childOfName("berthShip").attr("shape", {
                                        width: r + t.target.position[0]
                                    }), o.childOfName("beginCircle").attr("shape", {
                                        cx: t.target.position[0] + r
                                    }), o.childOfName("endCircle").attr("shape", {
                                        cx: 0
                                    }), o.childOfName("shipText").eachChild(function (t) {
                                        "right" === t.name && (t.invisible = !0)
                                    })
                                }
                                if (i) {
                                    h.crossGroup.removeAll();
                                    var a = c._getXCrossLine(t.offsetX);
                                    h.crossGroup.add(a);
                                    var s = c._getYCrossLine(t.offsetY);
                                    h.crossGroup.add(s)
                                }
                            }), a.on("mousemove", function (t) {
                                t.cancelBubble = !0
                            }), a.on("mousedown", function (t) {
                                1 === t.which ? (h.hdLineDragging = !0, h.hdShipDragging = !1, h.curBorderId = this.id, a.draggable = !0) : (h.hdLineDragging = !1, h.hdShipDragging = !1, h.curBorderId = "", a.draggable = !1)
                            }), (0, s.default)(window).on("mouseup", {
                                lineTop: a
                            }, function (t) {
                                h.hdLineDragging && h.curBorderId === t.data.lineTop.id && t.data.lineTop.trigger("mouseup"), h.curBorderId || (h.hdLineDragging = !1)
                            }), a.on("mouseup", function (r) {
                                if (h.crossGroup && h.hdLineDragging && h.curBorderId === this.id) {
                                    h.hdLineDragging = !1, h.hdShipDragging = !1, h.curShipGroupId = "", this.position = [0, 0], h.curBorderId = "";
                                    var n = h.crossGroup.childCount();
                                    if (n > 0) {
                                        var i = !1,
                                            o = 0,
                                            a = {};
                                        if (h.crossGroup.eachChild(function (t) {
                                            if (t.hdOverBorder) return i = !0, !1;
                                            "x" === t.hdtype && t.hddata && (n > 2 ? "right" === t.name && (a = t) : a = t)
                                        }), a && (o = a.hdpos), i || o + c.opt.shipOpt.width < e.xScope[0]) c._freshShipPosition(e, 0), c._updateShipXY(t, e);
                                        else if (a) {
                                            var s = e.xScope[1] - a.hdpos;
                                            t.planEndBollardCode = a.hddata.bollardCode, t.planEndBollardName = a.hddata.bollardName, e.xScope[1] = a.hdpos, c._freshShipWidthPosition(e, s), c._updateShipXY(t, e), "function" == typeof c.opt.shipSaveHandler && c.opt.shipSaveHandler(t)
                                        }
                                    }
                                }
                            }), i.add(a), i
                        }
                    }, {
                        key: "_resizeXPosByShipLength",
                        value: function (t, e) {
                            for (; t[1].pos - t[0].pos < e.shipLength / c.opt.xAxis.scale;) {
                                var r = -1,
                                    n = 0;
                                if (h.xData[t[1].index + 1]) r = t[1].index + 1, n = 1;
                                else {
                                    if (!h.xData[t[0].index - 1]) break;
                                    r = t[0].index - 1, n = 0
                                } if (-1 !== r) {
                                    var i = {};
                                    i.index = r, i.pos = h.xData[r].bollardDistance / c.opt.xAxis.scale + h.zeroPosition[0], t[n] = i
                                }
                            }
                        }
                    }]), t
                }();
            e.default = f, t.exports = e.default
        },
        function (t, e, r) {
            Object.defineProperty(e, "__esModule", {
                value: !0
            }), e.ShipBerth = void 0;
            var n, i = r(102),
                o = (n = i) && n.__esModule ? n : {
                    default: n
                };
            e.ShipBerth = o.default
        }
    ])
});