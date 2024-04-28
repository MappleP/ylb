import regs from "@/utils/regs";

const filters = {
    fmtDecimal3(v) {
        if (!v) {
            return v;
        }
        if (isNaN(v)) {
            v = v - 0;
        }
        return v.toFixed(3);

    },
    fmtPhone(v) {
        return v.substr(0, 3) + '****' + v.substr(7);
    },
    fmtMoney(v) {
        if (isNaN(v)) return v;
        return parseInt(v).toFixed(2).replace(regs.replace.thChar, ",");
    }
};
export default filters;