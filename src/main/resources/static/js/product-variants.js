// product-variants.js
// Vanilla JS to update displayed stock and price based on selected color/size.
document.addEventListener('DOMContentLoaded', function () {
    var rows = Array.prototype.slice.call(document.querySelectorAll('table.variant-table tbody tr'));
    if (!rows.length) return;

    var parsed = rows.map(function (tr) {
        return {
            idmau: String(tr.dataset.idmau || ''),
            mausac: (tr.cells[0] && tr.cells[0].textContent.trim()) || '',
            idsize: String(tr.dataset.idsize || ''),
            size: (tr.cells[1] && tr.cells[1].textContent.trim()) || '',
            dongia: Number(tr.dataset.dongia) || 0,
            soluongton: Number(tr.dataset.soluongton) || 0
        };
    });

    var colorSelect = document.getElementById('colorSelect');
    var sizeSelect = document.getElementById('sizeSelect');
    var stockInfo = document.getElementById('stockInfo');
    var mainPrice = document.getElementById('mainPrice');
    var addToCart = document.getElementById('addToCart');

    function uniqueBy(arr, key) {
        var seen = {};
        return arr.filter(function (item) {
            if (!item) return false;
            if (seen[item[key]]) return false;
            seen[item[key]] = true;
            return true;
        });
    }

    function updateStockAndPrice() {
        var selColor = colorSelect ? colorSelect.value : '';
        var selSize = sizeSelect ? sizeSelect.value : '';
        var found = null;

        if (selColor && selSize) {
            for (var i = 0; i < parsed.length; i++) {
                if (parsed[i].idmau === selColor && parsed[i].idsize === selSize) { found = parsed[i]; break; }
            }
        } else if (selColor || selSize) {
            for (var j = 0; j < parsed.length; j++) {
                if ((selColor && parsed[j].idmau === selColor) || (selSize && parsed[j].idsize === selSize)) {
                    if (!found) found = { dongia: parsed[j].dongia, soluongton: 0 };
                    found.soluongton += parsed[j].soluongton;
                }
            }
        } else {
            found = { dongia: parsed[0].dongia, soluongton: 0 };
            parsed.forEach(function (p) { found.soluongton += p.soluongton; });
        }

        if (found) {
            if (stockInfo) stockInfo.textContent = found.soluongton;
            if (mainPrice) mainPrice.textContent = found.dongia.toLocaleString('vi-VN') + '₫';
            if (addToCart) addToCart.disabled = !(found.soluongton > 0);
        } else {
            if (stockInfo) stockInfo.textContent = '0';
            if (addToCart) addToCart.disabled = true;
        }
    }

    if (colorSelect) colorSelect.addEventListener('change', updateStockAndPrice);
    if (sizeSelect) sizeSelect.addEventListener('change', updateStockAndPrice);

    updateStockAndPrice();

    if (addToCart) {
        addToCart.addEventListener('click', function () {
            var selColor = colorSelect ? colorSelect.value : '';
            var selSize = sizeSelect ? sizeSelect.value : '';
            if (!selColor || !selSize) { alert('Vui lòng chọn màu và size'); return; }
            // TODO: Replace with real add-to-cart logic
            alert('Đã thêm sản phẩm vào giỏ (demo)');
        });
    }
});
