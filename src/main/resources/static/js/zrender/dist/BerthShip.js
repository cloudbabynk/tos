import zrender from 'zrender';
let BerthShip = zrender.Path.extend({
    type: 'prow',
    shape: {
        // x, y on the cusp
        x: 0,
        y: 0,
        width: 0,
        height: 0,
        prowWidthRatio: 0.75,
        prowMaxWidth: 30,
        arrow: 'R',
    },
    buildPath: function (path, shape) {
        let x = shape.x;
        let y = shape.y;
        let w = shape.width;
        let h = shape.height;
        let arrow = shape.arrow;
        let prowWidth = Math.min(shape.prowMaxWidth, shape.prowWidthRatio * w);
        let bodyWidth = w - prowWidth;
        if (arrow === 'R') {
            path.moveTo(x, y);
            path.lineTo(x, y + h);
            path.lineTo(x + bodyWidth, y + h);
            path.lineTo(x + w, y + h / 2);
            path.lineTo(x + bodyWidth, y);
        } else {
            path.moveTo(x + w, y);
            path.lineTo(x + w, y + h);
            path.lineTo(x + prowWidth, y + h);
            path.lineTo(x, y + h / 2);
            path.lineTo(x + prowWidth, y);
        }
        path.closePath();
    }
});
export default BerthShip;