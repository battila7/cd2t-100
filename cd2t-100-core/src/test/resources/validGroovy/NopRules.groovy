@Opcode("NOP")
@Rules(["clampat", "overflow"])
class Nop {
  static apply(ExecutionEnvironment execEnv) {
    return;
  }
}
