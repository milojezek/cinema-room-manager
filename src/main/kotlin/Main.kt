package cinema

fun main() {
    while (Cinema.roomManagerIsOn) {
        Cinema.selectAction()
    }
}