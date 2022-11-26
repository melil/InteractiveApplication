package ru.ilyamelnichenko.interactiveapplication.domain.cases

import ru.ilyamelnichenko.interactiveapplication.adapter.AdapterItem
import ru.ilyamelnichenko.interactiveapplication.adapter.delegates.XYAdapterItem
import ru.ilyamelnichenko.interactiveapplication.api.responses.ArrayOfXYResponse
import javax.inject.Inject

class GenerateTableListUseCase @Inject constructor() :
    UseCase<GenerateTableListUseCase.Param, GenerateTableListUseCase.Output> {

    override suspend fun run(param: Param): CaseResult<Output> {
        val adapterItems = mutableListOf<AdapterItem>()
        param.points.points?.forEach {
            adapterItems.add(
                XYAdapterItem(
                    title = "x:${it.x}",
                    value = "y:${it.y}",
                    uniqueTag = "points"
                )
            )
        }
        return Success(Output(adapterItems))
    }

    class Param(val points: ArrayOfXYResponse)

    data class Output(val list: MutableList<AdapterItem>)
}