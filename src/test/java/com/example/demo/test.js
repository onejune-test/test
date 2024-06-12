/**
 * 检查一个字符串是否可以被解析为有效的正则表达式。
 * @param {string} str 需要检查的字符串。
 * @returns {boolean} 如果字符串可以被解析为正则表达式返回true，否则返回false。
 */
function isRegExp(str) {
    try {
        new RegExp(str); // 尝试将输入字符串解析为正则表达式
        return true; // 如果没有抛出异常，表示字符串可以被解析为一个正则表达式
    } catch (e) {
        console.log(e) // 捕获到异常，表示字符串不符合正则表达式的语法规则
        return false; // 返回false表示解析失败
    }
}
