/**
 * NULL 의 사용.
 *
 * 사용 가능한 경우.
 *  - 나중에 값을 할당할 변수를 초기화 할 때
 *  - 선언한 변수에 값이 할당되었는지 비교할때
 *  - 인자 값으로 객체를 넘기는 함수를 호출할 때
 *  - 함수를 호출한 곳에서 반환값으로 객체를 기대할 때
 *
 * 사용 불가능한 경우.
 *  - 함수의 인자 값을 확인하기 위해 null로 비교해서는 안된다.
 *  - 초기화되지 않은 변수를 null로 비교해서는 안된다.
 * */

/**
 * 데이터 타입 검사
 *
 * 주의 : null 은 변수에 값이 할당되었는지 확인할 때 사용하면 안된다.
 *       null 이 허용되는 경우는 기대하는 값이 정말 null 일 경우에만 사용한다.
 *       반드시 '===' 또는 '!==' 을 사용한다.
 *       ex) if(value !== null){...}
 **/

/**
 * String 검사
 *
 * @param value
 * @return {boolean}
 * */
function isString(value) {
    return typeof value === 'string';
}

/**
 * Number 검사
 *
 * @param value
 * @return {boolean}
 * */
function isNumber(value) {
    return typeof value === "number";
}

/**
 * Boolean 검사
 *
 * @param value
 * @return {boolean}
 * */
function isBoolean(value) {
    return typeof value === "boolean" && value;
}

/**
 * Undefined 검사
 * 주의 : "undefined" 의 경우, 선언한 변수이든 선언하지 않은 변수이든
 *        둘다 "undefined" 로 반환한다.
 *
 * @param value
 * @return {boolean}
 * */
function isUndefined(value) {
    return typeof value === "undefined"
}

/**
 * 객체 참조타입 검사(Object, Array, Date, Error)
 *
 * 주의 : 1. 모든 객체는 Object 를 기본적으로 상속받으므로 어떤 객체든지
 *        instanceof Object 를 사용하면 true 를 반환한다.
 *        때문에 instanceof Object 를 사용하면 무슨 타입인지 알수 없다.
 *
 *        2. 사용자 정의 타입을 구분할 때도 "instanceof" 사용.
 *        단, 같은 프레임 내에서만 사용한다. 다른 프레임간에는 instanceof 를 사용하지 않는다.
 **/

/**
 * Date 객체인지 확인
 *
 * @param value
 * @return {boolean}
 **/
function isDate(value) {
    return value instanceof Date;
}

/**
 * RegExp 객체인지 확인
 *
 * @param value
 * @return {boolean}
 **/
function isRegExp(value) {
    return value instanceof RegExp;
}

/**
 * Error 객체인지 확인
 *
 * @param value
 * @return {boolean}
 **/
function isError(value) {
    return value instanceof Error;
}

/**
 * 함수 검사
 *
 * 함수는 참조 타입으로 "Function" 이라는 생성자가 존재,
 * 모든 함수는 "Function" 의 인스턴스이다.
 *
 * 주의 : IE8 이하 버전은 DOM 에 관련된 함수에 "function" 이 아닌 "Object" 로 반환.
 * */
function isFunction(value) {
    return typeof value === "function";
}

// IE8 이하 DOM 관련 함수 검사
function isFunctionForIe(value) {
    return value in document;
}

/**
 * Array 검사
 *
 * @param value
 * @returns {boolean}
 */
function isArray(value) {
    if (typeof Array.isArray === "function") {
        return Array.isArray(value);
    } else {
        return Object.prototype.toString.call(value) === "[object Array]";
    }
}

/**
 * 프로퍼티 검사
 *
 * 인스턴스에 존재한는 프로퍼티인지만 검사한다.
 *
 * 주의 : IE8 이하 버전의 DOM 객체는 Object 를 상속받지 않아 hasOwnProperty() 메서드가 없다.
 *        가급적 in 연산자를 사용하고, 인스턴스의 프로퍼티인지 확인이 필요할때만
 *        hasOwnProperty()를 사용.
 *
 * @param value 프로퍼티값
 * @param object 인스턴스
 * @return {boolean}
 * */
function hasProperty(value, object) {
    return value in object;
}

/**
 *
 * @returns {boolean}
 */
function isEmpty(value) {
    return isUndefined(value) || value === '' || value === null || value !== value;
}

function numberWithCommas(x) {
    return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}