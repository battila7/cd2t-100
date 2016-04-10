package hu.progtech.cd2t100.computation;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;

import hu.progtech.cd2t100.asm.InstructionElement;
import hu.progtech.cd2t100.asm.CodeElementSet;
import hu.progtech.cd2t100.asm.LineNumberedException;

import hu.progtech.cd2t100.formal.InstructionInfo;

import hu.progtech.cd2t100.computation.io.Register;
import hu.progtech.cd2t100.computation.io.CommunicationPort;

public final class InstructionFactory {
	private ArgumentChecker argumentChecker;

	private final InstructionRegistry instructionRegistry;

	private final Map<String, Register> registerMap;
	private final Map<String, CommunicationPort> readablePortMap;
	private final Map<String, CommunicationPort> writeablePortMap;

	private final ArrayList<LineNumberedException> exceptionList;
	private final ArrayList<Instruction> instructions;

	public InstructionFactory(InstructionRegistry instructionRegistry,
														Map<String, Register> registerMap,
														Map<String, CommunicationPort> readablePortMap,
														Map<String, CommunicationPort> writeablePortMap)
	{
		this.instructionRegistry = instructionRegistry;
		this.registerMap = registerMap;
		this.readablePortMap = readablePortMap;
		this.writeablePortMap = writeablePortMap;

		exceptionList = new ArrayList<>();
		instructions = new ArrayList<>();
	}

	public List<LineNumberedException> makeInstructions(CodeElementSet elementSet) {
		elementSet.getInstructionList()
							.stream()
							.forEach(x -> constructInstruction(x));

		return exceptionList;
	}

	public List<LineNumberedException> getExceptionList() {
		return exceptionList;
	}

	public List<Instruction> getInstructions() {
		return instructions;
	}

	private void constructInstruction(InstructionElement element) {
		InstructionInfo info =
			instructionRegistry.getInstructionInfoFor(element.getOpcode());

		if (info == null) {
			// no opcode, get some exception

			return;
		}


	}
}
